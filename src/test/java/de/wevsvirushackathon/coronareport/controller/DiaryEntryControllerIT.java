package de.wevsvirushackathon.coronareport.controller;

import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.User;
import de.wevsvirushackathon.coronareport.diary.TypeOfContract;
import de.wevsvirushackathon.coronareport.repository.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class DiaryEntryControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiaryEntryRepository DiaryEntryRepository;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

        createTestData();

        MvcResult result = mvc.perform(get("/dep/1/diary_entry/csv").header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(new MediaType("text", "csv")))
                .andReturn();

        Assertions.assertEquals(
                new Scanner(new File("src/test/resources/expected_csv.csv")).useDelimiter("\\Z").next().trim(),
                result.getResponse().getContentAsString().trim());
    }

    private void createTestData() {
        final User user = User.builder().firstname("Bob").surename("Korona").healthDepartmentId("1").build();
        patientRepository.save(user);
//        DiaryEntryRepository.save(DiaryEntry.builder().user(user)
//                .dateTime(dateOf(2020, 1, 10)).bodyTemperature(23).typeOfContract(TypeOfContract.AE).build());
//        DiaryEntryRepository.save(DiaryEntry.builder().user(user)
//                .dateTime(dateOf(2020, 1, 11)).bodyTemperature(30).typeOfContract(TypeOfContract.AE).build());
//
//        final User user2 = User.builder().firstname("Alice").surename("Wonderland").healthDepartmentId("2").build();
//        patientRepository.save(user2);
//        DiaryEntryRepository.save(DiaryEntry.builder().user(user2)
//                .dateTime(dateOf(2020, 1, 10)).bodyTemperature(23).typeOfContract(TypeOfContract.Aer).build());
//        DiaryEntryRepository.save(DiaryEntry.builder().user(user2)
//                .dateTime(dateOf(2020, 1, 11)).bodyTemperature(23).typeOfContract(TypeOfContract.AE).build());
    }

    public Date dateOf(int year, int month, int day) {
        return java.util.Date.from(LocalDate.of(year, month, day).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
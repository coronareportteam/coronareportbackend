package de.wevsvirushackathon.coronareport.symptomes;

import de.wevsvirushackathon.coronareport.contactperson.ContactPerson;
import de.wevsvirushackathon.coronareport.contactperson.ContactPersonRepository;
import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.diary.TypeOfContract;
import de.wevsvirushackathon.coronareport.diary.TypeOfProtection;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartment;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartmentRepository;
import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientRepository;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class SymptomDiaryControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

        MvcResult result = mvc.perform(get("/diaryentries/export/csv/Testamt1/aba0ec65-6c1d-4b7b-91b4-c31ef16ad0a2").header("Origin", "*")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(new MediaType("text", "csv")))
                .andReturn();

        Assertions.assertEquals(
                new Scanner(new File("src/test/resources/expected_csv.csv")).useDelimiter("\\Z").next().trim(),
                result.getResponse().getContentAsString().trim());
    }
}
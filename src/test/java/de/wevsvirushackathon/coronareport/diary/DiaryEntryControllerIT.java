package de.wevsvirushackathon.coronareport.diary;

import de.wevsvirushackathon.coronareport.contactperson.ContactPerson;
import de.wevsvirushackathon.coronareport.contactperson.ContactPersonRepository;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
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
    private ClientRepository clientRepository;

    @Autowired
    private DiaryEntryRepository diaryEntryRepository;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

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
        final Client client = Client.builder().firstname("Bob").surename("Korona").healthDepartmentId("1").build();
        final ContactPerson cp1 = ContactPerson.builder().client(client).firstname("Alice").surename("Someone")
                .typeOfContract(TypeOfContract.AE)
                .typeOfProtection(TypeOfProtection.H)
                .build();
        final ContactPerson cp2 = ContactPerson.builder().client(client).firstname("Boris").surename("Wanabe")
                .typeOfContract(TypeOfContract.Aer)
                .typeOfProtection(TypeOfProtection.M1)
                .build();

        clientRepository.save(client);
        contactPersonRepository.saveAll(Arrays.asList(cp1, cp2));
        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client)
                .dateTime(dateOf(2020, 1, 10))
                .bodyTemperature(23)
                .build());
        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client)
                .dateTime(dateOf(2020, 1, 11))
                .bodyTemperature(30)
                .contactPersons(Arrays.asList(cp1, cp2))
                .build());

        final Client client2 = Client.builder().firstname("Alice").surename("Wonderland").healthDepartmentId("2").build();
        clientRepository.save(client2);
        diaryEntryRepository.save(DiaryEntry.builder().client(client2)
                .dateTime(dateOf(2020, 1, 10)).bodyTemperature(23).build());
        diaryEntryRepository.save(DiaryEntry.builder().client(client2)
                .dateTime(dateOf(2020, 1, 11)).bodyTemperature(23).build());
    }

    public Timestamp dateOf(int year, int month, int day) {
        return new Timestamp(Date.from(LocalDate.of(year, month, day).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()).getTime());
    }
}
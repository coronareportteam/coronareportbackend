package de.wevsvirushackathon.coronareport.controller;

import de.wevsvirushackathon.coronareport.diary.Contact;
import de.wevsvirushackathon.coronareport.diary.Patient;
import de.wevsvirushackathon.coronareport.repository.ContactRepository;
import de.wevsvirushackathon.coronareport.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class ContractControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

        createTestData();

        mvc.perform(get("/export")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(new MediaType("text", "csv")));
    }

    private void createTestData() {
        final Patient patient = Patient.builder().firstname("Bob").surename("Korona").build();
        patientRepository.save(patient);
        contactRepository.save(Contact.builder()
                .firstname("Alice")
                .surename("Wonderland")
                .email("alice@wonderland.de")
                .patient(patient).build());
    }

}
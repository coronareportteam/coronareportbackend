package de.wevsvirushackathon.coronareport;

import de.wevsvirushackathon.coronareport.contactperson.ContactPerson;
import de.wevsvirushackathon.coronareport.contactperson.ContactPersonRepository;
import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.diary.TypeOfContract;
import de.wevsvirushackathon.coronareport.diary.TypeOfProtection;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartment;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartmentRepository;
import de.wevsvirushackathon.coronareport.symptomes.Symptom;
import de.wevsvirushackathon.coronareport.symptomes.SymptomRepository;
import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientRepository;
import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
//@Profile("!prod")
public class DummyDataInputBean implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private ClientRepository clientRepository;
    private ContactPersonRepository contactPersonRepository;
    private DiaryEntryRepository diaryEntryRepository;
    private HealthDepartmentRepository healthDepartmentRepository;
    private SymptomRepository symptomRepository;

    public DummyDataInputBean(ClientRepository clientRepository,
                              ContactPersonRepository contactPersonRepository,
                              DiaryEntryRepository diaryEntryRepository,
                              HealthDepartmentRepository healthDepartmentRepository,
                              SymptomRepository symptomRepository) {
        this.clientRepository = clientRepository;
        this.contactPersonRepository = contactPersonRepository;
        this.diaryEntryRepository = diaryEntryRepository;
        this.healthDepartmentRepository = healthDepartmentRepository;
        this.symptomRepository = symptomRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final HealthDepartment hd1 = this.healthDepartmentRepository.save(HealthDepartment.builder().fullName("Testamt 1")
                .id("Testamt1").passCode(UUID.fromString("aba0ec65-6c1d-4b7b-91b4-c31ef16ad0a2")).build());
        final HealthDepartment hd2 = this.healthDepartmentRepository.save(HealthDepartment.builder().fullName("Testamt 2")
                .id("Testamt2").passCode(UUID.fromString("ca3f3e9a-414a-4117-a623-59b109b269f1")).build());

        final Client client1 = clientRepository.save(Client.builder().firstname("Fabian").surename("Bauer").healthDepartmentId(hd1.getId()).build());
        final Client client2 = clientRepository.save(Client.builder().firstname("Sabine").surename("Wohlfart").healthDepartmentId(hd1.getId()).build());
        final Client client3 = clientRepository.save(Client.builder().firstname("Alice").surename("Wonderland").healthDepartmentId(hd2.getId()).build());

        final ContactPerson cp1 = contactPersonRepository.save(ContactPerson.builder().client(client1).firstname("Alice").surename("Sommer")
                .typeOfContract(TypeOfContract.AE)
                .typeOfProtection(TypeOfProtection.H)
                .build());

        final ContactPerson cp2 = contactPersonRepository.save(ContactPerson.builder().client(client1).firstname("Boris").surename("Johnson")
                .typeOfContract(TypeOfContract.Aer)
                .typeOfProtection(TypeOfProtection.M1)
                .build());

        final ContactPerson cp3 = contactPersonRepository.save(ContactPerson.builder().client(client1).firstname("Roland").surename("Koch")
                .typeOfContract(TypeOfContract.O)
                .typeOfProtection(TypeOfProtection.Zero)
                .build());

        final Symptom s1 = symptomRepository.findAllByName("Angst").get(0);
        final Symptom s2 = symptomRepository.findAllByName("Kopfschmerzen").get(0);
        final Symptom s3 = symptomRepository.findAllByName("Fieber").get(0);
        final Symptom s4 = symptomRepository.findAllByName("Husten").get(0);
        final Symptom s5 = symptomRepository.findAllByName("Atemnot").get(0);

        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client1)
                .dateTime(dateOf(2020, 3, 26))
                .bodyTemperature(23)
                .symptoms(Collections.singletonList(s1))
                .build());
        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client1)
                .dateTime(dateOf(2020, 3, 27))
                .bodyTemperature(30)
                .symptoms(Arrays.asList(s1, s2, s3))
                .contactPersons(Arrays.asList(cp1, cp2))
                .build());

        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client2)
                .dateTime(dateOf(2020, 3, 25))
                .bodyTemperature(24)
                .build());
        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client2)
                .dateTime(dateOf(2020, 3, 26))
                .bodyTemperature(28)
                .symptoms(Collections.singletonList(s3))
                .build());
        diaryEntryRepository.save(DiaryEntry.builder()
                .client(client2)
                .dateTime(dateOf(2020, 3, 27))
                .bodyTemperature(30)
                .symptoms(Arrays.asList(s3, s4, s5))
                .contactPersons(Collections.singletonList(cp3))
                .build());

        diaryEntryRepository.save(DiaryEntry.builder().client(client3)
                .dateTime(dateOf(2020, 1, 10)).bodyTemperature(23).build());
        diaryEntryRepository.save(DiaryEntry.builder().client(client3)
                .dateTime(dateOf(2020, 1, 11)).bodyTemperature(23).build());
    }

    public Timestamp dateOf(int year, int month, int day) {
        return new Timestamp(Date.from(LocalDate.of(year, month, day).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()).getTime());
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}

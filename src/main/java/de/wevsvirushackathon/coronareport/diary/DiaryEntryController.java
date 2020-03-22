package de.wevsvirushackathon.coronareport.diary;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import de.wevsvirushackathon.coronareport.contactperson.ContactPerson;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartment;
import de.wevsvirushackathon.coronareport.healthdepartment.HealthDepartmentRepository;


@RestController
public class DiaryEntryController {

    private DiaryEntryRepository diaryEntryRepository;
    private HealthDepartmentRepository healthDepartmentRepository;

    public DiaryEntryController(DiaryEntryRepository diaryEntryRepository, HealthDepartmentRepository healthDepartmentRepository) {
        this.diaryEntryRepository = diaryEntryRepository;
        this.healthDepartmentRepository = healthDepartmentRepository;
    }

    @GetMapping("/dep/{healthDepartmentId}/{healthDepartmentPassCode}/diary_entry/csv")
    public void exportCSV(
            @PathVariable("healthDepartmentId") String healthDepartmentId,
            @PathVariable UUID healthDepartmentPassCode,
            HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = String.format("diary_entry_%s.csv", healthDepartmentId);

        // rudimentary security setting TODO replace after POC
        Optional<HealthDepartment> healthDepartment = this.healthDepartmentRepository.findById(healthDepartmentId);
        if (healthDepartment.isEmpty() || healthDepartment.get().getPassCode().equals(healthDepartmentPassCode)) {
            // TODO replacy by proper exception handling
            throw new IllegalAccessException("Wrong credentials for health department");
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        final Collection<DiaryEntry> diaryEntryCollection = diaryEntryRepository.findAllByHealthDepartmentIdNotTransmitted(healthDepartmentId);
        response.getWriter().println("clientId;firstname;surename;dateTime;bodyTemperature;symptoms;contactFirstname;contactSurename;typeOfContract;typeOfProtection");

        final String valueSep = ";";

        for (final DiaryEntry d : diaryEntryCollection) {
            final Collection<ContactPerson> contactPersonCollection = d.getContactPersons().size() == 0 ? Collections.singletonList(new ContactPerson()) : d.getContactPersons();
            for (final ContactPerson cp : contactPersonCollection) {
                response.getWriter().print(d.getClient().getClientId());
                response.getWriter().print(valueSep);
                response.getWriter().print(d.getClient().getFirstname());
                response.getWriter().print(valueSep);
                response.getWriter().print(d.getClient().getSurename());
                response.getWriter().print(valueSep);
                response.getWriter().print(d.getDateTime());
                response.getWriter().print(valueSep);
                response.getWriter().print(d.getBodyTemperature());
                response.getWriter().print(valueSep);
                response.getWriter().print(d.getSymptoms() == null ? "[]" : d.getSymptoms().toString());
                response.getWriter().print(valueSep);
                response.getWriter().print(cp.getFirstname() == null ? "" : cp.getFirstname());
                response.getWriter().print(valueSep);
                response.getWriter().print(cp.getSurename() == null ? "" : cp.getSurename());
                response.getWriter().print(valueSep);
                response.getWriter().print(cp.getTypeOfContract() == null ? "" : cp.getTypeOfContract().getLabel());
                response.getWriter().print(valueSep);
                response.getWriter().print(cp.getTypeOfProtection() == null ? "" : cp.getTypeOfProtection().getLabel());
                response.getWriter().println();
            }
        }

        // Save transmitted state.
        for (final DiaryEntry d : diaryEntryCollection) {
            d.setTransmittedToHealthDepartment(true);
            this.diaryEntryRepository.save(d);
        }
    }
}

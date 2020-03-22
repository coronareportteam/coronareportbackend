package de.wevsvirushackathon.coronareport.controller;

import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.repository.DiaryEntryRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@RestController
public class DiaryEntryController {

    private DiaryEntryRepository diaryEntryRepository;

    public DiaryEntryController(DiaryEntryRepository diaryEntryRepository) {
        this.diaryEntryRepository = diaryEntryRepository;
    }

    @GetMapping("/dep/{healthDepartmentId}/diary_entry/csv")
    public void exportCSV(@PathVariable("healthDepartmentId") String healthDepartmentId,
                          HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = String.format("diary_entry_%s.csv", healthDepartmentId);

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        response.getWriter().println("patientId;firstName;sureName;dateTime;bodyTemperature;symptoms;typeOfContract;typeOfProtection");

        final String valueSep = ";";

        for (final DiaryEntry d : new ArrayList<>(diaryEntryRepository.findAllByHealthDepartmentId(healthDepartmentId))) {
            response.getWriter().print(d.getUser().getPatientId());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getUser().getFirstname());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getUser().getSurename());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getDateTime());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getBodyTemperature());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getSymptoms() == null ? "[]" : d.getSymptoms().toString());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getTypeOfContract() == null ? "" : d.getTypeOfContract().getLabel());
            response.getWriter().print(valueSep);
            response.getWriter().print(d.getTypeOfProtection()  == null ? "" : d.getTypeOfProtection().getLabel());
            response.getWriter().println();
        }
    }
}

package de.wevsvirushackathon.coronareport.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import de.wevsvirushackathon.coronareport.diary.Contact;
import de.wevsvirushackathon.coronareport.service.ContactService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = "contact.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<Contact> writer = new StatefulBeanToCsvBuilder<Contact>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all users to csv file
        final List<Contact> contactList = new ArrayList<>();
        contactService.findAllContacts().forEach(contactList::add);
        writer.write(contactList);

    }
}

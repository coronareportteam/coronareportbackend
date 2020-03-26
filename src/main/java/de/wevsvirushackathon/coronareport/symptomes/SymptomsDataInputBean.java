package de.wevsvirushackathon.coronareport.symptomes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class SymptomsDataInputBean implements ApplicationListener<ContextRefreshedEvent> {

    private SymptomRepository repository;

    public SymptomsDataInputBean(SymptomRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("masterdata/symptoms.json");
        System.out.println(in);

        final ObjectMapper objectMapper = new ObjectMapper();

        //read json file and convert to customer object
        try {
            final List<Symptom> symptoms = objectMapper.readValue(in, new TypeReference<List<Symptom>>(){});
            if (this.repository.count() > 0) {
                return;
            }
            Long i = 1L;
            for (final Symptom symptom : symptoms) {
                symptom.setId(i);
                i++;
            }
            this.repository.saveAll(symptoms);
        } catch (IOException e) {
           throw new IllegalStateException("Unable to parse masterdata file", e);
        }
    }
}

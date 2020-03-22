package de.wevsvirushackathon.coronareport.diary;

import java.time.LocalDateTime;
import java.util.List;

import de.wevsvirushackathon.coronareport.contactperson.ContactPerson;
import de.wevsvirushackathon.coronareport.symptomes.Symptom;
import de.wevsvirushackathon.coronareport.user.Client;
import lombok.Getter;
import lombok.Setter;

public class DiaryEntryDtoOut {

    @Getter 
    @Setter
    private int id;

    @Getter @Setter
    private Client client;
    
    @Getter @Setter  
    private LocalDateTime dateTime;
    
    @Getter @Setter
    private float bodyTemperature;
    
    @Getter @Setter
    private List<Symptom> symptoms;

    @Getter @Setter
    private List<ContactPerson> contactPersonList;

    @Getter @Setter
    private boolean transmittedToHealthDepartment;
}

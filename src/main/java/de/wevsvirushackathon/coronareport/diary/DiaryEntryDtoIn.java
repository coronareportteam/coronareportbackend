package de.wevsvirushackathon.coronareport.diary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.wevsvirushackathon.coronareport.user.Client;
import lombok.Getter;
import lombok.Setter;

public class DiaryEntryDtoIn {

    @Getter 
    private int id;

    @Getter @Setter
    private Client client;
    
    @Getter @Setter  
    private Date dateTime;
    
    @Getter @Setter
    private float bodyTemperature;
    
    @Getter @Setter
    private List<Integer> symptoms = new ArrayList<>();;

    @Getter @Setter
    private List<Integer> contactPersonList = new ArrayList<>();

    @Getter @Setter
    private boolean transmittedToHealthDepartment;
}

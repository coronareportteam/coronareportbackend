package de.wevsvirushackathon.coronareport.diary;

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
    private List<Long> symptoms;
    
    @Getter @Setter
    private boolean transmittedToHealthDepartment;
}

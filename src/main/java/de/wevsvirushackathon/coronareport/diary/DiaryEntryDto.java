package de.wevsvirushackathon.coronareport.diary;

import java.util.Date;
import java.util.List;

import de.wevsvirushackathon.coronareport.user.Client;
import lombok.Getter;
import lombok.Setter;

public class DiaryEntryDto {

    private int id;

    @Getter @Setter
    private Client client;
    
    private Date dateTime;
    
    @Getter @Setter
    private float bodyTemperature;
    
    @Getter @Setter
    private List<Long> symptoms;
    
    private boolean transmittedToHealthDepartment;
}

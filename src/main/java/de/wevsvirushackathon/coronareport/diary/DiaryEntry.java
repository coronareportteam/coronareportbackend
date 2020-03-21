package de.wevsvirushackathon.coronareport.diary;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class DiaryEntry {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Patient patient;

    private LocalDateTime dateTime;

    private TypeOfContract typeOfContract;
    private TypeOfProtection typeOfProtection;
    
    private float bodyTemperature;
    @ManyToMany
    private List<Symptom> symptoms;
    
    private boolean transmittedToGesundheitsAmt;
}

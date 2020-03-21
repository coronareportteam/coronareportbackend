package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}

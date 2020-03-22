package de.wevsvirushackathon.coronareport.diary;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import de.wevsvirushackathon.coronareport.symptomes.Symptom;
import de.wevsvirushackathon.coronareport.user.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DiaryEntry {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    @Temporal(TemporalType.DATE)
    private Date dateTime;

    private float bodyTemperature;
    @ManyToMany
    private List<Symptom> symptoms;
    
    private boolean transmittedToHealthDepartment;
}

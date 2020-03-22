package de.wevsvirushackathon.coronareport.healthdepartment;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class HealthDepartment {
    @Id
    private String id;
    private String fullName;
    private UUID passCode;

}

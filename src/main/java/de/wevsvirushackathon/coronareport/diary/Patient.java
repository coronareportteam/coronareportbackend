package de.wevsvirushackathon.coronareport.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patient {

    @Id
    @GeneratedValue
    private Long patientId;
    private String patientCode;
    private String surename;
    private String firstname;
    private String phone;
    private String zipCode;

    private boolean infected;

    private String healthDepartmentId;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FirstReport> comments = new ArrayList<>();

}

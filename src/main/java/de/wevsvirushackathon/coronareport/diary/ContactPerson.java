package de.wevsvirushackathon.coronareport.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContactPerson {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User user;

    private String surename;

    private String firstname;

    private String phone;

    private String email;

    private TypeOfContract typeOfContract;

    private TypeOfProtection typeOfProtection;
}

package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Patient patient;

    private String surename;

    private String firstname;

    private String phone;

    private String email;
}

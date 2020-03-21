package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Patient {

    @Id
    @GeneratedValue
    private int userId;
    private String surename;
    private String firstname;
    private String phone;
    private boolean infected;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FirstReport> comments = new ArrayList<>();

}

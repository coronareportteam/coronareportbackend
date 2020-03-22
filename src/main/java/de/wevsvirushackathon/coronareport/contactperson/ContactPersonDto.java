package de.wevsvirushackathon.coronareport.contactperson;

import com.sun.istack.NotNull;
import de.wevsvirushackathon.coronareport.diary.TypeOfContract;
import de.wevsvirushackathon.coronareport.diary.TypeOfProtection;
import de.wevsvirushackathon.coronareport.user.Client;
import lombok.*;

import javax.persistence.*;

public class ContactPersonDto {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String surename;

    @Getter @Setter
    private String firstname;

    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private TypeOfContract typeOfContract;

    @Getter @Setter
    private TypeOfProtection typeOfProtection;
}

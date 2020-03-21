package de.wevsvirushackathon.coronareport.service;

import de.wevsvirushackathon.coronareport.diary.Contact;
import de.wevsvirushackathon.coronareport.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository repository;

    public ContactService(final ContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> findAllContacts() {
        return this.findAllContacts();
    }
}

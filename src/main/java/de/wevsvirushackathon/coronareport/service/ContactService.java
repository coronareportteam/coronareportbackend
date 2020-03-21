package de.wevsvirushackathon.coronareport.service;

import de.wevsvirushackathon.coronareport.diary.Contact;
import de.wevsvirushackathon.coronareport.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private ContactRepository repository;

    public ContactService(final ContactRepository repository) {
        this.repository = repository;
    }

    public Iterable<Contact> findAllContacts() {
        return repository.findAll();
    }
}

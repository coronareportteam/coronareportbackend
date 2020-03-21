package de.wevsvirushackathon.coronareport.repository;

import de.wevsvirushackathon.coronareport.diary.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository
        extends CrudRepository<Contact, Long> {
}
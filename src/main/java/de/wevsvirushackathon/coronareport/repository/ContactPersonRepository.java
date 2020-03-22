package de.wevsvirushackathon.coronareport.repository;

import de.wevsvirushackathon.coronareport.diary.ContactPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPersonRepository
        extends CrudRepository<ContactPerson, Long> {
}
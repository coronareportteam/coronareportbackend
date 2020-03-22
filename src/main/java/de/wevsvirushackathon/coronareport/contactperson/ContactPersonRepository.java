package de.wevsvirushackathon.coronareport.contactperson;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPersonRepository
        extends CrudRepository<ContactPerson, Long> {
}
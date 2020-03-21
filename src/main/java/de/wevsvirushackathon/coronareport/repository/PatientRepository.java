package de.wevsvirushackathon.coronareport.repository;

import de.wevsvirushackathon.coronareport.diary.Contact;
import de.wevsvirushackathon.coronareport.diary.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository
        extends CrudRepository<Patient, Long> {
}
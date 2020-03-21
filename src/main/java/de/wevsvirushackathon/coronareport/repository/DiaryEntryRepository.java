package de.wevsvirushackathon.coronareport.repository;

import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DiaryEntryRepository
        extends CrudRepository<DiaryEntry, Long> {
    @Query("SELECT d FROM DiaryEntry d WHERE d.patient.healthDepartmentId = :healthDepartmentId")
    Collection<DiaryEntry> findAllByHealthDepartmentId(@Param("healthDepartmentId") String healthDepartmentId);

    Iterable<DiaryEntry> findAllByPatient(Patient patient);
}
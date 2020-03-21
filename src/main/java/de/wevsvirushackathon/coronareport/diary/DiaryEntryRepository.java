package de.wevsvirushackathon.coronareport.diary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryEntryRepository
        extends CrudRepository<DiaryEntry, Long> {
	
	Iterable<DiaryEntry> findAllByPatient(Patient patient);
	
	
}
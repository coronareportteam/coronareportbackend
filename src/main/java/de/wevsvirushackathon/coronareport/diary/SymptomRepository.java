package de.wevsvirushackathon.coronareport.diary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository
        extends CrudRepository<Symptom, Long> {
	
	
}
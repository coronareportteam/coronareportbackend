package de.wevsvirushackathon.coronareport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wevsvirushackathon.coronareport.diary.Patient;

@Repository
public interface PatientRepository
        extends CrudRepository<Patient, Long> {
	
		public Patient findByPatientCode(String patientCode);
		
		
}
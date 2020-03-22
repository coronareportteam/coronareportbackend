package de.wevsvirushackathon.coronareport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wevsvirushackathon.coronareport.diary.User;

@Repository
public interface PatientRepository
        extends CrudRepository<User, Long> {
	
		public User findByPatientCode(String patientCode);
		
		
}
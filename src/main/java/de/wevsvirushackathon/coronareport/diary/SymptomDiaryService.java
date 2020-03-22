package de.wevsvirushackathon.coronareport.diary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wevsvirushackathon.coronareport.repository.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.repository.PatientRepository;

@RestController
public class SymptomDiaryService {
	
	@Autowired
	DiaryEntryRepository diarayEntryRepository;
	@Autowired
	PatientRepository patientRepository;
	
	
	public DiaryEntry storeEntry(DiaryEntry entry) {
		return entry;
	}
	
	/**
	 * Returns all diary entries of a patient
	 * @param patientCode the identifier of the patient
	 * @return
	 */
	@GetMapping("/diaryEntries")
	public Iterable<DiaryEntry> getDiaryEntries(String patientCode){
		Patient patient = patientRepository.findByPatientCode(patientCode);
		return diarayEntryRepository.findAllByPatient(patient);
	}
	


}

package de.wevsvirushackathon.coronareport.diary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@GetMapping("/symptoms")
	public List<Symptom> getSymptoms() {
		List<Symptom> symptoms = new ArrayList<>();
		
		symptoms.add(new Symptom(1, "Kopfschmerzen", true));
		symptoms.add(new Symptom(2, "Halsschmerzen", true));
		symptoms.add(new Symptom(3, "Schüttelfrost", true));
		symptoms.add(new Symptom(4, "Ausschlag", false));
		symptoms.add(new Symptom(5, "Durchfall", true));
		
		return symptoms;
	}

}

package de.wevsvirushackathon.coronareport.diary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomDiaryService {
	
	
	public DiaryEntry storeEntry(DiaryEntry entry) {
		return entry;
	}
	
	
	@GetMapping("/symptoms")
	public List<Symptom> getSymptoms() {
		List<Symptom> symptoms = new ArrayList<>();
		
		symptoms.add(new Symptom(1, "Kopfschmerzen"));
		symptoms.add(new Symptom(2, "Halsschmerzen"));
		symptoms.add(new Symptom(3, "Schüttelfrost"));
		
		return symptoms;
	}

}

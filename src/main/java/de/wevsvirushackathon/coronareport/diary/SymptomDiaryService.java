package de.wevsvirushackathon.coronareport.diary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomService {
	
	
	
	public SymptomValueEntry storeEntry(SymptomValueEntry entry) {
		return null;
	}
	
	
	@GetMapping("/symptoms")
	public List<Symptom> getSymptoms() {
		List<Symptom> symptoms = new ArrayList<>();
		
		symptoms.add(new Symptom("Kopfschmerzen"));
		symptoms.add(new Symptom("Halsschmerzen"));
		symptoms.add(new Symptom("Schüttelfrost"));
		
		return symptoms;
	}

}

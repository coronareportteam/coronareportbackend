package de.wevsvirushackathon.coronareport.masterdataupdater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.wevsvirushackathon.coronareport.symptomes.Symptom;
import de.wevsvirushackathon.coronareport.symptomes.SymptomRepository;

@RestController
class SymptomMDUpdateController {
	
	@Autowired
	private SymptomRepository repo;
	
	
	/**
	 * Stores a new Symptom
	 * @param symptom
	 * @return
	 */
	@PostMapping("/symptom")
	public Symptom addSymptom(@RequestBody Symptom symptom) {
		return this.repo.save(symptom);
	}
	
	/**
	 * Stores an array 
	 * @param symptom
	 * @return
	 */
	@PostMapping("/symptoms")
	public Iterable<Symptom> addSymptoms(@RequestBody Iterable<Symptom> symptoms) {
		return this.repo.saveAll(symptoms);
	}	
	
}

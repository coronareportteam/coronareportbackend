package de.wevsvirushackathon.coronareport.symptomes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomController {
	
	private SymptomRepository repo;

	@Autowired
	public SymptomController(SymptomRepository repo) {
		this.repo = repo;
	}

	/**
	 * Returns all symptom entries. Should be used as master-data for other api calls;
	 * @return
	 */
	@GetMapping("/symptoms")
	public Iterable<Symptom> getSymptoms() {
		return repo.findAll();
	}

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

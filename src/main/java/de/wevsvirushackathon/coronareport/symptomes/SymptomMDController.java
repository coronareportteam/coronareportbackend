package de.wevsvirushackathon.coronareport.symptomes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomMDController {
	
	@Autowired
	private SymptomRepository repo;
	
	/**
	 * Returns all symptom entries. Should be used as master-data for other api calls;
	 * @return
	 */
	@GetMapping("/symptoms")
	public Iterable<Symptom> getSymptoms() {
		return repo.findAll();
	}

	
}

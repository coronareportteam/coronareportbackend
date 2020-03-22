package de.wevsvirushackathon.coronareport.contactperson;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ContactPersonController {

	private ContactPersonRepository repo;

	private ContactPersonController(final ContactPersonRepository repo) {
		this.repo = repo;
	}

	@PostMapping("/contact_person")
	public ContactPerson addContactPerson(@RequestBody ContactPerson contactPerson) {
		return this.repo.save(contactPerson);
	}

}

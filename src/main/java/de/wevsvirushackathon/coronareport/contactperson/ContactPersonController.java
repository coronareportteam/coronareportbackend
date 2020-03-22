package de.wevsvirushackathon.coronareport.contactperson;

import org.springframework.web.bind.annotation.*;

@RestController
class ContactPersonController {

	private ContactPersonRepository repo;

	private ContactPersonController(final ContactPersonRepository repo) {
		this.repo = repo;
	}

	@PostMapping("/contact")
	public ContactPerson addContactPerson(@RequestBody ContactPerson contactPerson) {
		return this.repo.save(contactPerson);
	}

	@PutMapping("/contact")
	public ContactPerson updateContactPerson(@RequestBody ContactPerson contactPerson) {
		return this.repo.save(contactPerson);
	}

	@GetMapping("/contact")
	public Iterable<ContactPerson> getContacts() {
		return this.repo.findAll();
	}

}

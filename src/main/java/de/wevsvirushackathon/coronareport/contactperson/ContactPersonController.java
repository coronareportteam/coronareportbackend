package de.wevsvirushackathon.coronareport.contactperson;

import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/contact")
class ContactPersonController {

	private ClientRepository clientRepository;
	private ContactPersonRepository repo;
	private ModelMapper modelMapper;

	private ContactPersonController(final ContactPersonRepository repo,
									final ClientRepository clientRepository,
									final ModelMapper modelMapper) {
		this.repo = repo;
		this.clientRepository = clientRepository;
		this.modelMapper = modelMapper;
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	@GetMapping("/")
	public Iterable<ContactPersonDto> getContacts(@RequestHeader("client-code") String clientCode) {

		final Iterable<ContactPerson> entries = this.repo.findAllByClientCode(clientCode);

		ArrayList<ContactPersonDto> dtos = new ArrayList<>();
		entries.forEach(x -> dtos.add(modelMapper.map(x, ContactPersonDto.class)));
		return dtos;
	}

	@PostMapping("/")
	public ContactPersonDto addContactPerson(@RequestBody ContactPersonDto contactPersonDto,
											 @RequestHeader("client-code") String clientCode) {
		final Client client = clientRepository.findByClientCode(clientCode);
		final ContactPerson contactPerson = modelMapper.map(contactPersonDto, ContactPerson.class);
		contactPerson.setClient(client);
		this.repo.save(contactPerson);
		contactPersonDto.setId(contactPerson.getId());
		return contactPersonDto;
	}

	@PutMapping("/{contactId}")
	public ContactPersonDto updateContactPerson(@RequestBody ContactPersonDto contactPersonDto,
												@RequestHeader("client-code") String clientCode,
												@PathVariable("contactId") Long contactId) {
		final Client client = clientRepository.findByClientCode(clientCode);
		final ContactPerson contactPerson = modelMapper.map(contactPersonDto, ContactPerson.class);
		contactPerson.setClient(client);
		contactPerson.setId(contactId);
		this.repo.save(contactPerson);
		return contactPersonDto;
	}

	@GetMapping("/{contactId}")
	public ContactPersonDto getContactById(@PathVariable("contactId") Long contactId) {
		// TODO: NotFound check
		return modelMapper.map(this.repo.findById(contactId).get(), ContactPersonDto.class);
	}
}

package de.wevsvirushackathon.coronareport.symptomes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import de.wevsvirushackathon.coronareport.contactperson.ContactPerson;
import de.wevsvirushackathon.coronareport.contactperson.ContactPersonRepository;
import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryDtoIn;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryDtoOut;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The controller for creating, updating and reading of diaryEntries by a client
 * 
 * @author Patrick Otto
 *
 */
@RestController
public class SymptomDiaryController {

	@Autowired
	DiaryEntryRepository diarayEntryRepository;
	@Autowired
	ClientRepository userRepository;
	@Autowired
	ContactPersonRepository contactPersonRepository;
	@Autowired
	SymptomRepository symptomRepository;

	@Autowired
	private ModelMapper modelMapper;
	

	/**
	 * Saves a new diary entry
	 * 
	 * @param clientCode    The code of the client provided by teh Health department
	 *                      or by the application after first registration
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	@ApiOperation(value = "Create a new diary entry", response = DiaryEntryDtoOut.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Entity successfully created"),
			@ApiResponse(code = 500, message = "Internal Server error")
	})
	@PostMapping("/diaryentries")
	public ResponseEntity<DiaryEntryDtoOut> storeEntry(
			@ApiParam(value = "The header variable containing the client-code", required = true) @RequestHeader("client-code") String clientCode,
			@ApiParam(value = "The DiaryEntry to be created", required = true) @RequestBody DiaryEntryDtoIn diaryEntryDto) {

		DiaryEntry validDiaryEntry;
		try {
			validDiaryEntry = prepareAndValidateEntry(diaryEntryDto, clientCode);
		} catch (ParseException e) {
			return new ResponseEntity<DiaryEntryDtoOut>(HttpStatus.BAD_REQUEST);
		}

		validDiaryEntry = diarayEntryRepository.save(validDiaryEntry);

		ResponseEntity<DiaryEntryDtoOut> response = ResponseEntity.status(HttpStatus.CREATED)
				.body(convertToDto(validDiaryEntry));

		return response;
	}

	/**
	 * Updates an existing new diary entry
	 * 
	 * @param clientCode    The code of the client provided by teh Health department
	 *                      or by the application after first registration
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	@ApiOperation(value = "Updates an existing  diary entry")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Entity successfully updated"),
			@ApiResponse(code = 400, message = "If DiaryEntry could not be parsed or path id and id in body differs."),			
			@ApiResponse(code = 404, message = "If DiaryEntry with given Id does not exist"),
			@ApiResponse(code = 500, message = "Internal Server error")
	})	
	@PutMapping("/diaryentries/{id}")
	public ResponseEntity<?> updateEntry(
			@ApiParam(value = "The header variable containing the client-code", required = true) @RequestHeader("client-code") String clientCode,
			@ApiParam(value = "The DiaryEntry to be updated", required = true) @RequestBody DiaryEntryDtoIn diaryEntryDto,
			@ApiParam(value = "The id of the entity that should be updated", required = true)  @PathVariable String id){
		
		
		// check if pathId and body id match
		int diaryEntryId = (int) Integer.parseInt(id);
		if(diaryEntryId != diaryEntryDto.getId()) {
			return ResponseEntity.badRequest().build();
		}
		
		// check if diaryentry exists
		if(!diarayEntryRepository.existsById((long) diaryEntryId)) {
			return ResponseEntity.notFound().build();
		}	
		
		DiaryEntry validDiaryEntry;
		try {
			validDiaryEntry = prepareAndValidateEntry(diaryEntryDto, clientCode);
		} catch (ParseException e) {
			return new ResponseEntity<DiaryEntryDtoOut>(HttpStatus.BAD_REQUEST);
		}

		diarayEntryRepository.save(validDiaryEntry);

		return ResponseEntity.ok().build();
	}

	
	/**
	 * Validates if all mandatory information is given an transforms the dto into
	 * the model object
	 * 
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	private DiaryEntry prepareAndValidateEntry(DiaryEntryDtoIn diaryEntryDto, String clientCode) throws ParseException {
		DiaryEntry diaryEntry = convertToEntity(diaryEntryDto, clientCode);

		// check if date is set
		if (diaryEntry.getDateTime() == null) {
			throw new IllegalArgumentException("Attribute DateTime must not be null");
		}

		return diaryEntry;

	}
	

	/**
	 * Returns all diary entries of a client
	 * 
	 * @param clientCode the identifier of the client
	 * @return
	 * @throws ParseException
	 */
	@ApiOperation(value = "Retrieves all diaryEntries of a client ordered by their date descending")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Entities successfully fetched"),
			@ApiResponse(code = 500, message = "Internal Server error")	
			})
	@GetMapping("/diaryentries")
	public Iterable<DiaryEntryDtoOut> getDiaryEntries(
		@ApiParam(value = "The header variable containing the client-code", required = true)
		@RequestHeader("client-code") String clientCode) throws ParseException {

		Client client = userRepository.findByClientCode(clientCode);

		Iterable<DiaryEntry> entries = diarayEntryRepository.findAllByClientOrderByDateTimeDesc(client);

		ArrayList<DiaryEntryDtoOut> dtos = new ArrayList<>();

		entries.forEach(x -> dtos.add(convertToDto(x)));

		return dtos;
	}

	
	/**
	 * Returns a single diaryentry identified by its id
	 * 
	 * @param clientCode the identifier of the client
	 * @param id         the identifier of the diaryentry
	 * @return
	 * @throws ParseException
	 */
	@ApiOperation(value = "Returns a single diary entry")
	@GetMapping("/diaryentries/{id}")
	public ResponseEntity<DiaryEntryDtoOut> getDiaryEntry(
			@ApiParam(value = "The header variable containing the client-code", required = true) @RequestHeader("client-code") String clientCode,
			@ApiParam(value = "The id of the requested diaryEntry element", required = true) @PathVariable String id) throws ParseException {

		int diaryEntryId = (int) Integer.parseInt(id);

		Optional<DiaryEntry> diaryEntryOptional = diarayEntryRepository.findById((long) diaryEntryId);

		if (diaryEntryOptional.isPresent()) {
			return ResponseEntity.ok(convertToDto(diaryEntryOptional.get()));
		} else {
			return new ResponseEntity<DiaryEntryDtoOut>(HttpStatus.BAD_REQUEST);
		}

	}

	
	/**
	 * Converts a model object into its DTO representation
	 * 
	 * @param diaryEntry
	 * @return
	 */
	private DiaryEntryDtoOut convertToDto(DiaryEntry diaryEntry) {
		DiaryEntryDtoOut diaryEntryDto = modelMapper.map(diaryEntry, DiaryEntryDtoOut.class);
		
		// contacts attribute has different names in dto and model, so map it manually
		diaryEntryDto.setContactPersonList(diaryEntry.getContactPersons());

		// set Id expicitly because Long is not mapped automatically
		diaryEntryDto.setId(diaryEntry.getId().intValue());

		if (diaryEntry.getDateTime() != null) {
			diaryEntryDto.setDateTime(diaryEntry.getDateTime().toLocalDateTime());
		}

		return diaryEntryDto;
	}

	
	/**
	 * Converts a Dto object into the model object
	 * 
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	private DiaryEntry convertToEntity(DiaryEntryDtoIn diaryEntryDto, String clientCode) throws ParseException {
		DiaryEntry entry = modelMapper.map(diaryEntryDto, DiaryEntry.class);

		// map id explicitly, because datatype Long is not mapped automatically
		if (diaryEntryDto.getId() == 0) {
			entry.setId(null);
		} else {
			entry.setId((long) diaryEntryDto.getId());
		}

		// lookup client by clientcode
		Client client = userRepository.findByClientCode(clientCode);
		if (client == null) {
			throw new IllegalArgumentException("No matching client for given client-code");
		}
		entry.setClient(client);

		// lookup symptoms
		ArrayList<Symptom> resolvedSymptoms = new ArrayList<>();

		for (long symptomId : diaryEntryDto.getSymptoms()) {

			symptomRepository.findById(symptomId).ifPresent(resolvedSymptoms::add);
		}
		entry.setSymptoms(resolvedSymptoms);

		// lookup ContactPersons
		ArrayList<ContactPerson> resolvedContactPerson = new ArrayList<>();

		for (long contactPersonId : diaryEntryDto.getContactPersonList()) {

			contactPersonRepository.findById(contactPersonId).ifPresent(resolvedContactPerson::add);
		}
		entry.setContactPersons(resolvedContactPerson);

		return entry;
	}

}

package de.wevsvirushackathon.coronareport.symptomes;

import java.text.ParseException;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryDtoIn;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryDtoOut;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientRepository;

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
	SymptomRepository symptomRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Saves a new diary entry
	 * 
	 * @param clientCode
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/diaryEntries")
	public ResponseEntity<DiaryEntryDtoOut> storeEntry(@RequestHeader("client-code") String clientCode,
			@RequestBody DiaryEntryDtoIn diaryEntryDto) {

		DiaryEntry validDiaryEntry;
		try {
			validDiaryEntry = prepareAndValidateEntry(diaryEntryDto, clientCode);
		} catch (ParseException e) {
			return new ResponseEntity<DiaryEntryDtoOut>(HttpStatus.BAD_REQUEST);
		}

		validDiaryEntry = diarayEntryRepository.save(validDiaryEntry);

		return ResponseEntity.ok(convertToDto(validDiaryEntry));

	}

	/**
	 * Updates an existing new diary entry
	 * 
	 * @param clientCode
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	@PutMapping("/diaryEntries")
	public ResponseEntity<?> updateEntry(@RequestHeader("client-code") String clientCode,
			@RequestBody DiaryEntryDtoIn diaryEntryDto) {

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
	 * Validates if all mandatory information is given an transforms the dto into the
	 * model object
	 * 
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException 
	 */
	private DiaryEntry prepareAndValidateEntry(DiaryEntryDtoIn diaryEntryDto, String clientCode) throws ParseException {
		DiaryEntry diaryEntry = convertToEntity(diaryEntryDto,clientCode);
		
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
	@GetMapping("/diaryEntries")
	public Iterable<DiaryEntryDtoOut> getDiaryEntries(@RequestHeader("client-code") String clientCode)
			throws ParseException {

		Client client = userRepository.findByClientCode(clientCode);

		Iterable<DiaryEntry> entries = diarayEntryRepository.findAllByClientOrderByDateTimeDesc(client);

		ArrayList<DiaryEntryDtoOut> dtos = new ArrayList<>();

		entries.forEach(x -> dtos.add(convertToDto(x)));

		return dtos;
	}

	/**
	 * Converts a model object into its DTO representation
	 * 
	 * @param diaryEntry
	 * @return
	 */
	private DiaryEntryDtoOut convertToDto(DiaryEntry diaryEntry) {
		DiaryEntryDtoOut diaryEntryDto = modelMapper.map(diaryEntry, DiaryEntryDtoOut.class);

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
		if(diaryEntryDto.getId() == 0) {
			entry.setId(null);
		} else {
			entry.setId( (long) diaryEntryDto.getId());
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

			Symptom symptom = symptomRepository.findById(symptomId).orElse(null);
			if (symptom != null) {
				resolvedSymptoms.add(symptom);
			}
		}
		entry.setSymptoms(resolvedSymptoms);

		return entry;

	}

}

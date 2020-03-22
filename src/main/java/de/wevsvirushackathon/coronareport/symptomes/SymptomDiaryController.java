package de.wevsvirushackathon.coronareport.symptomes;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * @param clientCode
     * @param diaryEntryDto
     * @return
     * @throws ParseException
     */
	@PostMapping("/diaryEntries")
	public ResponseEntity<DiaryEntryDtoOut> storeEntry(@RequestHeader("client-code") String clientCode, @RequestBody DiaryEntryDtoIn diaryEntryDto) throws ParseException {
		
		DiaryEntry diaryEntry = convertToEntity(diaryEntryDto);
		
		// lookup client by clientcode
		Client client = userRepository.findByClientCode(clientCode);
		if(client == null) {
			ResponseEntity<DiaryEntryDtoOut> response = new ResponseEntity<DiaryEntryDtoOut>(HttpStatus.BAD_REQUEST);
			return response; 
		}
		
		// check if date is set
		if(diaryEntry.getDateTime() == null) {
			ResponseEntity<DiaryEntryDtoOut> response = new ResponseEntity<DiaryEntryDtoOut>(HttpStatus.BAD_REQUEST);
			return response; 
		}
		
		diaryEntry.setClient(client);
		
		diaryEntry = diarayEntryRepository.save(diaryEntry);
		
		return ResponseEntity.ok(convertToDto(diaryEntry));

	}
	
	/**
	 * Returns all diary entries of a client
	 * @param clientCode the identifier of the client
	 * @return
	 * @throws ParseException 
	 */
	@GetMapping("/diaryEntries")
	public Iterable<DiaryEntryDtoOut> getDiaryEntries(@RequestHeader("client-code") String clientCode) throws ParseException{
		
		Client client = userRepository.findByClientCode(clientCode);
		
		Iterable<DiaryEntry> entries =  diarayEntryRepository.findAllByClientOrderByDateTimeDesc(client);
		
		ArrayList<DiaryEntryDtoOut> dtos = new ArrayList<>();
		
		entries.forEach( x -> dtos.add(convertToDto(x)));
		
		return dtos;
	}
	
	/**
	 * Converts a model object into its DTO representation
	 * @param diaryEntry
	 * @return
	 */
	private DiaryEntryDtoOut convertToDto(DiaryEntry diaryEntry) {
		DiaryEntryDtoOut diaryEntryDto = modelMapper.map(diaryEntry, DiaryEntryDtoOut.class);

		if(diaryEntry.getDateTime() != null) {
			
			diaryEntryDto.setDateTime(diaryEntry.getDateTime().toLocalDateTime());
			
		}
		
	    return diaryEntryDto;
	}
	
	/**
	 * Converts a Dto object into the model object
	 * @param diaryEntryDto
	 * @return
	 * @throws ParseException
	 */
	private DiaryEntry convertToEntity(DiaryEntryDtoIn diaryEntryDto) throws ParseException {
		DiaryEntry entry = modelMapper.map(diaryEntryDto, DiaryEntry.class);
		
		// lookup symptoms 
		ArrayList<Symptom> resolvedSymptoms = new ArrayList<>();
		
		for(long symptomId: diaryEntryDto.getSymptoms()) {
			
			Symptom symptom= symptomRepository.findById(symptomId).orElse(null);
			if(symptom != null){
				resolvedSymptoms.add(symptom);
			}
		}
		entry.setSymptoms(resolvedSymptoms);
		
		return entry;
	
	}


}

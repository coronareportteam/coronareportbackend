package de.wevsvirushackathon.coronareport.symptomes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryDto;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.user.Client;
import de.wevsvirushackathon.coronareport.user.ClientRepository;

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
	
	@PostMapping("/diaryEntries")
	public DiaryEntryDto storeEntry(@RequestHeader("client-code") String clientCode, @RequestBody DiaryEntryDto diaryEntryDto) throws ParseException {
		
		DiaryEntry diaryEntry = convertToEntity(diaryEntryDto);
		
		// replace client client
		Client client = userRepository.findByClientCode(clientCode);
		diaryEntry.setClient(client);
		
		diarayEntryRepository.save(diaryEntry);
		
	//	List<Symptom> symptoms = resolveSymptoms(diaryEntry.getSymptoms());
		//diaryEntry.setSymptoms(symptoms);
		return convertToDto(diaryEntry);

	}
	
//	private List<Symptom> resolveSymptoms(List<Symptom> symptoms) {
//		
//		ArrayList<Symptom> resolvedSymptoms = new ArrayList<>();
//		
//		for(Symptom symptom: symptoms) {
//			
//			resolvedSymptoms = symptomRepository.findById(symptoms)
//		}
//		return null;
//	}

	/**
	 * Returns all diary entries of a client
	 * @param clientCode the identifier of the client
	 * @return
	 * @throws ParseException 
	 */
	@GetMapping("/diaryEntries")
	public Iterable<DiaryEntryDto> getDiaryEntries(@RequestHeader("client-code") String clientCode) throws ParseException{
		

		
		Client client = userRepository.findByClientCode(clientCode);
		
//		List<Symptom> symptoms =  new ArrayList<Symptom>();
//		symptoms.add(new Symptom(6, "Durchfall" , false));
//		symptoms.add(new Symptom(8, "Hautausschlag" , false));
//		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		
//		Client client = new Client((long) 10,"AGD3004aG", "Otto", "Patrick", null, null, true, null, null);
//		DiaryEntry entry = new DiaryEntry((long) 1, client, format.parse("2020-03-14"), (float) 37.5,symptoms, true);
//		
//		DiaryEntry entry2 = new DiaryEntry((long) 2, client, format.parse("2020-03-15"), (float) 37.7,symptoms, false);
//		
//		ArrayList<DiaryEntry> results = new ArrayList<DiaryEntry>();
//		results.add(entry);
//		results.add(entry2);
	
//		return results;
		
		
		Iterable<DiaryEntry> entries =  diarayEntryRepository.findAllByClientOrderByDateTimeAsc(client);
		
		ArrayList<DiaryEntryDto> dtos = new ArrayList<>();
		
		entries.forEach( x -> dtos.add(convertToDto(x)));
		
		return dtos;
	}
	
	private DiaryEntryDto convertToDto(DiaryEntry diaryEntry) {
		DiaryEntryDto diaryEntryDto = modelMapper.map(diaryEntry, DiaryEntryDto.class);

	    return diaryEntryDto;
	}
	
	private DiaryEntry convertToEntity(DiaryEntryDto diaryEntryDto) throws ParseException {
		DiaryEntry entry = modelMapper.map(diaryEntryDto, DiaryEntry.class);
		
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

package de.wevsvirushackathon.coronareport.symptomes;


import de.wevsvirushackathon.coronareport.diary.DiaryEntry;
import de.wevsvirushackathon.coronareport.diary.DiaryEntryRepository;
import de.wevsvirushackathon.coronareport.user.ClientRepository;
import de.wevsvirushackathon.coronareport.user.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomDiaryController {
	
	@Autowired
	DiaryEntryRepository diarayEntryRepository;
	@Autowired
	ClientRepository userRepository;
	
	
	public DiaryEntry storeEntry(DiaryEntry entry) {
		return entry;
	}
	
	/**
	 * Returns all diary entries of a patient
	 * @param patientCode the identifier of the patient
	 * @return
	 */
	@GetMapping("/diaryEntries")
	public Iterable<DiaryEntry> getDiaryEntries(String patientCode){
		Client client = userRepository.findByPatientCode(patientCode);
		return diarayEntryRepository.findAllByPatient(client);
	}
	


}

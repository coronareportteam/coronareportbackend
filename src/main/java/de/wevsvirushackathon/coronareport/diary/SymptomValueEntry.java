package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class SymptomValueEntry {
	
	@Id
	private Long id;
	@ManyToOne
	private DiaryEntry diaryEntry;

	@ManyToOne
	private Symptom symptom;

	@ManyToOne
	private Patient patient;
}

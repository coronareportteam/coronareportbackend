package de.wevsvirushackathon.coronareport.diary;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SymptomValueEntry {
	
	@Id
	private Long id;
	private LocalDateTime datetime;
	private Symptom symptpom;
	private float value;
	private Patient patient;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Symptom getSypmtpom() {
		return symptpom;
	}
	public void setSypmtpom(Symptom sypmtpom) {
		this.symptpom = sypmtpom;
	}
	

}

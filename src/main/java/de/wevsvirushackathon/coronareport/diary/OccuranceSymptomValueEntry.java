package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class OccuranceSymptomValueEntry extends SymptomValueEntry {
	
	private SymptomOccurance occurance;

}

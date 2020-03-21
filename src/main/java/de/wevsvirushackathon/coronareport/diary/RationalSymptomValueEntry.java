package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class RationalSymptomValueEntry extends SymptomValueEntry {
	
	private float scale;

}

package de.wevsvirushackathon.coronareport.diary;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class IntegerSymptomValueEntry extends SymptomValueEntry {
	
	private int scale;

}

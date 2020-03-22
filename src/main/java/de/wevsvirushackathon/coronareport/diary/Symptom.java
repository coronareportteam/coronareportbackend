package de.wevsvirushackathon.coronareport.diary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Symptom {

	@Id
	@GeneratedValue
	@Getter
	private int id;
	@Getter @Setter
	private String name;
	@Getter @Setter	
	private boolean isCharacteristic; 
	
	Symptom(){
		// noArgs Constructor
	}
	
	Symptom(int id, String name, boolean isCharacteristic) {
		this.setName(name);
		this.id = id;
		this.isCharacteristic = isCharacteristic;
	}

}

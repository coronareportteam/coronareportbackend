package de.wevsvirushackathon.coronareport.diary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Symptom {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	
	
	Symptom(int id, String name) {
		this.setName(name);
		this.setId(id);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

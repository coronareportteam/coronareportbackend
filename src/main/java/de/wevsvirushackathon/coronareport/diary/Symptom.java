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

}

package de.wevsvirushackathon.coronareport.symptomes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}

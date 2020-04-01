package de.wevsvirushackathon.coronareport.infrastructure.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RequestArgumentNotReadableException extends Exception {

	private static final long serialVersionUID = -1556638317220260960L;
	
	private String argument;
	private String message;
}

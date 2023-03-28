package com.capgemini.vehiclebreakdown.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class InvalidLoginException extends Exception {
	public InvalidLoginException() {

	}
	public InvalidLoginException(String s) {
		super(s);
	}
}

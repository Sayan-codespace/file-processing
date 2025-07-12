package com.fileprocessing.mail.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidExtensionException.class)
	public ResponseEntity<Object> handleInvalidExtensionException(InvalidExtensionException exception) {
		return ResponseEntity.status(500).body(exception.getMessage());
	}
}

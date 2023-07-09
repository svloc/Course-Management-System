package com.cms.exception;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), exception.getMessage(),
				request.getDescription(false), "HTTP 500");
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public final ResponseEntity<ExceptionResponse> handleNotFoundException(AdmissionInvalidException exception,
			WebRequest request) {
		ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), exception.getMessage(),
				request.getDescription(false), "HTTP 404");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

}

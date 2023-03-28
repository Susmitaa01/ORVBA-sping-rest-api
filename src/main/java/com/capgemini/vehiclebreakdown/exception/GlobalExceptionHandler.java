package com.capgemini.vehiclebreakdown.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> objectBody = new LinkedHashMap<>();
		objectBody.put("Timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
		objectBody.put("errorCode", status.value());
		List<String> exceptions = ex.getBindingResult().getFieldErrors().stream().map(m -> m.getDefaultMessage())
				.collect(Collectors.toList());
		objectBody.put("errorMessages", exceptions);

		return new ResponseEntity<Object>(objectBody, status);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(UserNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(BlockByAdminException.class)
	public ResponseEntity<ExceptionResponse> handler(BlockByAdminException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<ExceptionResponse> handler(InvalidLoginException  ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	@ExceptionHandler(MechanicNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(MechanicNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(AdminNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	@ExceptionHandler(RequestNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handler(RequestNotFoundException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ExceptionResponse> handler(ExpiredJwtException ex) {
		ExceptionResponse exception = new ExceptionResponse("Jwt Token Expired", LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ExceptionResponse> handler(SignatureException ex) {
		ExceptionResponse exception = new ExceptionResponse("Invalid JWT Token Signature", LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionResponse> handler(AccessDeniedException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> handler(IllegalArgumentException ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
	
//	
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<ExceptionResponse> handler(HttpMessageNotReadableException ex) {
//		ExceptionResponse exception = new ExceptionResponse("Invalid Request Body", LocalDateTime.now(),
//				HttpStatus.OK.value());
//		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
//				HttpStatus.OK);
//		return response;
//	}
//	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handler(Exception ex) {
		ExceptionResponse exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(),
				HttpStatus.OK.value());
		ResponseEntity<ExceptionResponse> response = new ResponseEntity<ExceptionResponse>(exception,
				HttpStatus.OK);
		return response;
	}
}

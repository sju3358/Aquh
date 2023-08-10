package com.ssafy.team8alette.global.handler;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {

	@ExceptionHandler(ParseException.class)
	public ResponseEntity memberPasswordInvalidHandler(ParseException exception) {

		Map<String, Object> responseData = new HashMap<>();
		responseData.put("errorMessage", exception.getMessage());

		return ResponseEntity
			.status(401)
			.body("");
	}
	
}

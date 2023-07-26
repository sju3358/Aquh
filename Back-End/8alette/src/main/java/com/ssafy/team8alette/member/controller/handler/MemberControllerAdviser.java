package com.ssafy.team8alette.member.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.member.exception.NullValueException;
import com.ssafy.team8alette.member.exception.RegexException;

@RestControllerAdvice
public class MemberControllerAdviser {

	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<Map<String, Object>> nullValueHandler(NullValueException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(token, status);
	}
	
	@ExceptionHandler(RegexException.class)
	public ResponseEntity<Map<String, Object>> regexHandler(RegexException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(token, status);
	}

}

package com.ssafy.team8alette.domain.feed.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.global.exception.NullValueException;

@RestControllerAdvice
public class FeedControllerAdviser {
	@ExceptionHandler(PropertyValueException.class)
	public ResponseEntity<Map<String, Object>> nullValueHandler(PropertyValueException exception) {
		Map<String, Object> nullCheck = new HashMap<>();
		nullCheck.put("message", "null이존재합니다.");
		nullCheck.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(nullCheck, status);
	}

	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<Map<String, Object>> nullValueHandler(NullValueException exception) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("message", exception.getMessage());
		errorResponse.put("status", 400);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}

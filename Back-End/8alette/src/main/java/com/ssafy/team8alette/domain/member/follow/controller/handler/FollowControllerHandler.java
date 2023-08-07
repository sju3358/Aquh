package com.ssafy.team8alette.domain.member.follow.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.domain.member.follow.exception.FollowNotFoundException;

@RestControllerAdvice
public class FollowControllerHandler {

	@ExceptionHandler(FollowNotFoundException.class)
	public ResponseEntity<Map<String, Object>> followNotFoundHandler(FollowNotFoundException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 500);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return new ResponseEntity<>(token, status);
	}
}

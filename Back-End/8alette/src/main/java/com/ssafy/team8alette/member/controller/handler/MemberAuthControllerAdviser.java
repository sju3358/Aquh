package com.ssafy.team8alette.member.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.member.exception.DuplicatedMemberException;
import com.ssafy.team8alette.member.exception.InvalidMemberPasswordException;
import com.ssafy.team8alette.member.exception.NullValueException;

@RestControllerAdvice
public class MemberAuthControllerAdviser {

	@ExceptionHandler(InvalidMemberPasswordException.class)
	public ResponseEntity<Map<String, Object>> invalidPassword(InvalidMemberPasswordException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("error", exception.getMessage());
		token.put("message", "fail");
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<>(token, status);
	}

	@ExceptionHandler(NullValueException.class)
	public ResponseEntity<Map<String, Object>> nullValue(NullValueException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("error", exception.getMessage());
		token.put("message", "fail");
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String, Object>>(token, status);
	}

	@ExceptionHandler(DuplicatedMemberException.class)
	public ResponseEntity<Map<String, Object>> duplicatedMemberId(DuplicatedMemberException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("error", exception.getMessage());
		token.put("message", "fail");
		HttpStatus status = HttpStatus.OK;
		System.out.println("a");
		return new ResponseEntity<Map<String, Object>>(token, status);
	}
}

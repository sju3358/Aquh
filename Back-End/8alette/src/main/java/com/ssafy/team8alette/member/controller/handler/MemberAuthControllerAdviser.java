package com.ssafy.team8alette.member.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.member.exception.InvalidTokenException;
import com.ssafy.team8alette.member.exception.MemberDuplicatedException;
import com.ssafy.team8alette.member.exception.MemberLoginException;
import com.ssafy.team8alette.member.exception.MemberNotExistException;
import com.ssafy.team8alette.member.exception.MemberPasswordInvalidException;
import com.ssafy.team8alette.member.exception.UnAuthorizedException;

@RestControllerAdvice
public class MemberAuthControllerAdviser {

	@ExceptionHandler(MemberPasswordInvalidException.class)
	public ResponseEntity<Map<String, Object>> memberPasswordInvalidHandler(MemberPasswordInvalidException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(token, status);
	}

	@ExceptionHandler(MemberNotExistException.class)
	public ResponseEntity<Map<String, Object>> memberNotExistHandler(MemberNotExistException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(token, status);
	}

	@ExceptionHandler(MemberDuplicatedException.class)
	public ResponseEntity<Map<String, Object>> memberDuplicatedHandler(MemberDuplicatedException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(token, status);
	}

	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<Map<String, Object>> unAuthHandler(UnAuthorizedException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 401);
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		return new ResponseEntity<>(token, status);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<Map<String, Object>> invalidTokenHandler(MemberDuplicatedException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 401);
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		return new ResponseEntity<>(token, status);
	}

	@ExceptionHandler(MemberLoginException.class)
	public ResponseEntity<Map<String, Object>> memberLoginException(MemberLoginException exception) {
		Map<String, Object> token = new HashMap<>();
		token.put("message", exception.getMessage());
		token.put("status", 401);
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		return new ResponseEntity<>(token, status);
	}
}

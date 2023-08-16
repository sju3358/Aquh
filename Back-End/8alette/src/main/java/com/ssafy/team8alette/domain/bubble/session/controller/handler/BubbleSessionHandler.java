package com.ssafy.team8alette.domain.bubble.session.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.team8alette.domain.bubble.session.exception.SessionNotFoundException;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;

@RestControllerAdvice
public class BubbleSessionHandler {

	@ExceptionHandler(OpenViduJavaClientException.class)
	public ResponseEntity<Map<String, Object>> oenViduJavaClientExceptionHandler(
		OpenViduJavaClientException exception) {
		Map<String, Object> nullCheck = new HashMap<>();
		nullCheck.put("message", "세션이 생성되지 않았습니다");
		nullCheck.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(nullCheck, status);
	}

	@ExceptionHandler(OpenViduHttpException.class)
	public ResponseEntity<Map<String, Object>> openViduHttpExceptionHandler(OpenViduHttpException exception) {

		Map<String, Object> nullCheck = new HashMap<>();
		nullCheck.put("message", "세션이 생성되지 않았습니다");
		nullCheck.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(nullCheck, status);
	}

	@ExceptionHandler(SessionNotFoundException.class)
	public ResponseEntity<Map<String, Object>> sessionNotFoundExceptionHandler(SessionNotFoundException exception) {

		Map<String, Object> nullCheck = new HashMap<>();
		nullCheck.put("message", "아직 세션이 존재하지 않습니다.");
		nullCheck.put("status", 400);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(nullCheck, status);
	}

}

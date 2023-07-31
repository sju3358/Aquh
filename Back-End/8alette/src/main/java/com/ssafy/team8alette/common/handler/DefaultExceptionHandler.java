package com.ssafy.team8alette.common.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<Map<String, Object>> runTimeException(Exception exception) {
	// 	Map<String, Object> token = new HashMap<>();
	// 	token.put("message", exception.getMessage());
	// 	token.put("status", 500);
	// 	HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	// 	return new ResponseEntity<>(token, status);
	// }
}

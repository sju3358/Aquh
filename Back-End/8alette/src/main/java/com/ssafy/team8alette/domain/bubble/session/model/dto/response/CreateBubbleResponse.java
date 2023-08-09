package com.ssafy.team8alette.domain.bubble.session.model.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public class CreateBubbleResponse {

	private String token;
	private HttpStatus status;
	private String message;
	
}

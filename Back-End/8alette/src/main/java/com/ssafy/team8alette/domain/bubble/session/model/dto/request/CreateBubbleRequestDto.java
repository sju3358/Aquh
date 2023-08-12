package com.ssafy.team8alette.domain.bubble.session.model.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateBubbleRequestDto {

	private Long hostMemberNumber;

	private Long categoryNumber;

	private boolean bubbleType;

	private String bubbleTitle;

	private String bubbleContent;
	
	private String bubbleThumbnail;

	private String planOpenDate;

	private String planCloseDate;

}

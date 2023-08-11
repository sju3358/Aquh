package com.ssafy.team8alette.domain.bubble.session.model.dto.request;

import java.time.LocalDateTime;

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

	//아 여기를 멀티파트로
	private String bubbleThumbnail;

	private LocalDateTime planOpenDate;

	private LocalDateTime planCloseDate;

}

package com.ssafy.team8alette.domain.bubble.session.model.dto.request;

import jakarta.annotation.Nullable;
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

	@Nullable
	private String planOpenDate;

	@Nullable
	private String planCloseDate;

}

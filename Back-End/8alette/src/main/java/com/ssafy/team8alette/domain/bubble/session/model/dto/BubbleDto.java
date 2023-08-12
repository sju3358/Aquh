package com.ssafy.team8alette.domain.bubble.session.model.dto;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BubbleDto {

	private Long bubbleNumber;

	private Long hostMemberNumber;

	private String categoryName;

	private boolean bubbleType;

	private String bubbleTitle;

	private String bubbleContent;

	private String bubbleThumbnail;

	private boolean bubbleState;

	@Nullable
	private LocalDateTime planOpenDate;

	@Nullable
	private LocalDateTime planCloseDate;

}

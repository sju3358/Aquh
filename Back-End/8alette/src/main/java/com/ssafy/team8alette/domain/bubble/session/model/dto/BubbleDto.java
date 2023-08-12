package com.ssafy.team8alette.domain.bubble.session.model.dto;

import java.time.LocalDateTime;

import com.ssafy.team8alette.domain.bubble.tools.model.entity.CategoryEntity;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BubbleDto {

	private Long bubbleNumber;

	private Long hostMemberNumber;

	private CategoryEntity categoryEntity;

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

package com.ssafy.team8alette.domain.bubble.session.model.dto;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BubbleParticipantDto {

	private Long bubbleNumber;

	private Long hostMemberNumber;

	@Nullable
	private boolean micStatus;

	@Nullable
	private boolean camStatus;

	@Nullable
	private boolean micLockStatus;

	@Nullable
	private boolean camLockStatus;

	@Nullable
	private boolean chatLockStatus;

	@Nullable
	private int joinStatus;

	private LocalDateTime createDate;
}

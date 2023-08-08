package com.ssafy.team8alette.domain.bubble.session.model.dto.request;

import java.util.Date;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateBubbleRequest {

	private Long hostMemberNumber;

	private Long categoryNumber;

	private boolean bubbleType;

	private String bubbleTitle;

	private String bubbleContent;

	private String bubbleThumbnail;

	private Date planOpenDate;

	private Date planCloseDate;

}

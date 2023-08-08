package com.ssafy.team8alette.domain.bubble.session.model.dto.response;

import java.util.Date;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.CategoryEntity;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateBubbleResponse {

	private Long bubbleNumber;

	private Member hostMemberNumber;

	private CategoryEntity categoryEntity;

	private boolean bubbleType;

	private String bubbleTitle;

	private String bubbleContent;

	private String bubbleThumbnail;

	private boolean bubbleState;

	private Date planOpenDate;

	private Date planCloseDate;
}

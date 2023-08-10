package com.ssafy.team8alette.domain.bubble.session.model.entity;

import java.time.LocalDateTime;

import com.ssafy.team8alette.domain.bubble.session.model.dto.BubbleDto;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.CategoryEntity;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bubble")
public class BubbleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bubble_number", nullable = false)
	private Long bubbleNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member hostMember;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_number", nullable = false)
	private CategoryEntity categoryEntity;

	@Column(name = "is_bubble_talk", nullable = false)
	private boolean bubbleType;

	@Column(name = "bubble_title", length = 20, nullable = false)
	private String bubbleTitle;

	@Column(name = "bubble_content", length = 500, nullable = false)
	private String bubbleContent;

	@Column(name = "bubble_thumbnail")
	private String bubbleThumbnail;

	@Column(name = "bubble_state", nullable = false)
	private boolean bubbleState;

	@Column(name = "plan_open_dttm")
	private LocalDateTime planOpenDate;

	@Column(name = "plan_close_dttm")
	private LocalDateTime planCloseDate;

	@Column(name = "create_dttm", nullable = false)
	private LocalDateTime createDate;

	@Column(name = "done_dttm")
	private LocalDateTime closeDate;

	public BubbleDto convertToDto() {
		return BubbleDto.builder()
			.bubbleNumber(this.bubbleNumber)
			.hostMemberNumber(this.hostMember.getMemberNumber())
			.bubbleTitle(this.bubbleTitle)
			.bubbleContent(this.bubbleContent)
			.bubbleThumbnail(this.bubbleThumbnail)
			.bubbleType(this.bubbleType)
			.categoryEntity(this.categoryEntity)
			.planCloseDate(this.planCloseDate)
			.planOpenDate(this.planOpenDate)
			.createDate(this.createDate)
			.closeDate(this.closeDate)
			.build();
	}

}

package com.ssafy.team8alette.domain.bubble.session.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ssafy.team8alette.domain.bubble.session.model.dto.BubbleParticipantDto;
import com.ssafy.team8alette.domain.bubble.session.model.dto.key.GroupID;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "bubble_list")
public class BubbleParticipantEntity implements Serializable {

	@EmbeddedId
	private GroupID groupID;

	@MapsId("bubbleNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bubble_number", nullable = false)
	private BubbleEntity bubbleEntity;

	@MapsId("memberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

	@Column(name = "is_mic_on", nullable = false)
	private boolean micStatus;

	@Column(name = "is_cam_on", nullable = false)
	private boolean camStatus;

	@Column(name = "is_mic_lock", nullable = false)
	private boolean micLockStatus;

	@Column(name = "is_cam_lock", nullable = false)
	private boolean camLockStatus;

	@Column(name = "is_chat_lock", nullable = false)
	private boolean chatLockStatus;

	@Column(name = "join_state", nullable = false)
	private int joinStatus;

	@Column(name = "create_dttm", nullable = false)
	private LocalDateTime createDate;

	public BubbleParticipantDto convertToDto() {
		return BubbleParticipantDto.builder()
			.bubbleNumber(this.bubbleEntity.getBubbleNumber())
			.hostMemberNumber(this.member.getMemberNumber())
			.createDate(this.createDate)
			.build();
	}
}

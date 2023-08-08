package com.ssafy.team8alette.domain.bubble.session.model.dto.entity;

import java.io.Serializable;
import java.util.Date;

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
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "bubble_list")
@Builder
public class BubbleListEntity implements Serializable {

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
	private Date createDate;
}

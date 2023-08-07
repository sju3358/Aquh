package com.ssafy.team8alette.domain.bubble.session.model.dto.entity;

import java.util.Date;

import com.ssafy.team8alette.domain.bubble.session.model.dto.key.TwoWayAnswerID;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "two_way_answer")
public class TwoWayAnswerEntity {
	@EmbeddedId
	private TwoWayAnswerID twoWayAnswerID;

	@MapsId("twoWayQuestionNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "two_way_question_number", nullable = false)
	private TwoWayQuestionEntity twoWayQuestionEntity;

	@MapsId("memberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

	@Column(name = "is_pick_right", nullable = false)
	private boolean isPickRight;

	@Column(name = "create_dttm", nullable = false)
	private Date createDate;
}

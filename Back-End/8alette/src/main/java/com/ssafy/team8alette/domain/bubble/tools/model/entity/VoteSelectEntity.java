package com.ssafy.team8alette.domain.bubble.tools.model.entity;

import java.io.Serializable;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.VoteID;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

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
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vote_select")
@ToString
public class VoteSelectEntity implements Serializable {

	@EmbeddedId
	private VoteID voteID;

	@MapsId("voteQuestionNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vote_question_number", nullable = false)
	private VoteQuestionEntity voteQuestionEntity;

	@MapsId("memberNumber")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_number", nullable = false)
	private Member member;

}

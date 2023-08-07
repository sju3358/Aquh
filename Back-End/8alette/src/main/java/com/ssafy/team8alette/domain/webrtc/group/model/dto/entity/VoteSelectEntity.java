package com.ssafy.team8alette.domain.webrtc.group.model.dto.entity;

import java.io.Serializable;

import com.ssafy.team8alette.domain.member.auth.model.dto.MemberEntity;
import com.ssafy.team8alette.domain.webrtc.group.model.dto.key.VoteID;

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
	private MemberEntity memberEntity;

}

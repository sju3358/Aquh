package com.ssafy.team8alette.domain.webrtc.group.model.dto.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class VoteID implements Serializable {

	private Long voteQuestionNumber;

	private Long memberNumber;

	public VoteID(Long voteQuestionNumber, Long memberNumber) {
		this.voteQuestionNumber = voteQuestionNumber;
		this.memberNumber = memberNumber;
	}
}

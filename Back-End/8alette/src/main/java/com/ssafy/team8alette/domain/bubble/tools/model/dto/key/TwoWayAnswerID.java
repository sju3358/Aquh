package com.ssafy.team8alette.domain.bubble.tools.model.dto.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class TwoWayAnswerID implements Serializable {

	private Long twoWayQuestionNumber;

	private Long memberNumber;

	private TwoWayAnswerID(Long twoWayQuestionNumber, Long memberNumber) {
		this.twoWayQuestionNumber = twoWayQuestionNumber;
		this.memberNumber = memberNumber;
	}

}

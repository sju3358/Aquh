package com.ssafy.team8alette.twowayanswer.model.dto.key;

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

	private Long twoWayAnswerNumber;

	private Long memberNumber;

	private TwoWayAnswerID(Long twoWayAnswerNumber, Long memberNumber) {
		this.twoWayAnswerNumber = twoWayAnswerNumber;
		this.memberNumber = memberNumber;
	}

}

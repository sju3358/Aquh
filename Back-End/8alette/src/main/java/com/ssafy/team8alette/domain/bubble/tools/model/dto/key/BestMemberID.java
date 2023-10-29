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
public class BestMemberID implements Serializable {

	private Long bubbleNumber;

	private Long memberNumber;

	private BestMemberID(Long bubbleNumber, Long memberNumber) {
		this.bubbleNumber = bubbleNumber;
		this.memberNumber = memberNumber;
	}

}

package com.ssafy.team8alette.domain.webrtc.openvidu.model.dto.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class GroupID implements Serializable {

	private Long bubbleNumber;

	private Long memberNumber;

	private GroupID(Long bubbleNumber, Long memberNumber) {
		this.bubbleNumber = bubbleNumber;
		this.memberNumber = memberNumber;
	}

}

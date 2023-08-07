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
public class BestMemberID implements Serializable {

	private Long roomNumber;

	private Long memberNumber;

	private BestMemberID(Long roomNumber, Long memberNumber) {
		this.roomNumber = roomNumber;
		this.memberNumber = memberNumber;
	}

}

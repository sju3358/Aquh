package com.ssafy.team8alette.domain.webrtc.openvidu.dto.key;

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

	private Long roomNumber;

	private Long memberNumber;

	private GroupID(Long roomNumber, Long memberNumber) {
		this.roomNumber = roomNumber;
		this.memberNumber = memberNumber;
	}

}

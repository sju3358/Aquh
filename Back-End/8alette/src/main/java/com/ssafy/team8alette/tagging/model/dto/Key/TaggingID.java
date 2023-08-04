package com.ssafy.team8alette.tagging.model.dto.Key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class TaggingID implements Serializable {

	private Long roomNumber;

	private Long hashTagNumber;

	public TaggingID(Long roomNumber, Long hashTagNumber) {
		this.roomNumber = roomNumber;
		this.hashTagNumber = hashTagNumber;
	}
}

package com.ssafy.team8alette.domain.hashtag.model.dto.Key;

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

	private Long bubbleNumber;

	private Long hashTagNumber;

	public TaggingID(Long bubbleNumber, Long hashTagNumber) {
		this.bubbleNumber = bubbleNumber;
		this.hashTagNumber = hashTagNumber;
	}
}

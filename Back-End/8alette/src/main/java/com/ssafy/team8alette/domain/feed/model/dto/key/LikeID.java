package com.ssafy.team8alette.domain.feed.model.dto.key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class LikeID implements Serializable {

	private Long likeFeedNumber;

	private Long likeMemberNumber;

	public LikeID(Long likeFeedNumber, Long likeMemberNumber) {
		this.likeFeedNumber = likeFeedNumber;
		this.likeMemberNumber = likeMemberNumber;
	}

}

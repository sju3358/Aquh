package com.ssafy.team8alette.follow.model.dto.Key;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class FollowID implements Serializable {

	@Column(name = "follower_number")
	private Long followerNumber;

	@Column(name = "follwing_number")
	private Long followingNumber;

	public FollowID(Long followerNumber, Long followingNumber) {
		this.followerNumber = followerNumber;
		this.followingNumber = followingNumber;
	}

}

package com.ssafy.team8alette.follow.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "follow")
public class Follow {

	@Id
	private Long followerMemberNumber;

	@Column
	private Long followingMemberNumber;
}

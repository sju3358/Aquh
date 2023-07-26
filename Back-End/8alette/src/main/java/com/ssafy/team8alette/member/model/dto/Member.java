package com.ssafy.team8alette.member.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_number", nullable = false)
	private Long memberNumber;

	@Column(name = "member_email", nullable = false)
	private String memberEmail;

	@Column(name = "member_password", nullable = false)
	private String memberPassword;

	@Column(name = "member_nickname", nullable = false)
	private String memberNickname;

	@Column(name = "member_intro")
	private String memberIntro;

	@Column(name = "member_state")
	private int memberState;

	@Column(name = "member_name")
	private String memberName;

	@Column(name = "member_age")
	private int memberAge;

}

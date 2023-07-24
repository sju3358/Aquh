package com.ssafy.team8alette.member.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "member")
public class MemberLoginInfo {

	@Id
	@GeneratedValue
	@Column(name = "member_number", nullable = false)
	private Long memberNumber;

	@Column(name = "member_token", nullable = false)
	private String refreshToken;
}


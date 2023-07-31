package com.ssafy.team8alette.member.model.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@RedisHash(value = "member_login_info")
public class MemberLoginInfo {

	@Id
	private Long memberNumber;
	private String refreshToken;
	private boolean isSocialLogin;
}


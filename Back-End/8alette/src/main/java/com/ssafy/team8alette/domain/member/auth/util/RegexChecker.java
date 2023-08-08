package com.ssafy.team8alette.domain.member.auth.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.ssafy.team8alette.global.exception.RegexException;

@Component
public class RegexChecker {

	private final String emailPattern = "^[a-z0-9\\.\\-_]+@([a-z0-9\\-]+\\.)+[a-z]{2,6}$";
	private final String nicknamePattern = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,8}$";
	private final String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$";

	public void checkEmailPattern(String targetString) {
		if (Pattern.matches(emailPattern, targetString) == false) {
			throw new RegexException("이메일 형식이 일치하지 않습니다.");
		}
	}

	public void checkNicknamePattern(String targetString) {
		if (Pattern.matches(nicknamePattern, targetString) == false) {
			throw new RegexException("닉네임 형식이 일치하지 않습니다.");
		}
	}

	public void checkPasswordPattern(String targetString) {
		if (Pattern.matches(passwordPattern, targetString) == false) {
			throw new RegexException("비밀번호 형식이 일치하지 않습니다.");
		}
	}
}

package com.ssafy.team8alette.member.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RegexChecker {

	//숫자랑 영어만
	private final String regexPattern1 = "^[a-zA-Z0-9]*$";

	//숫자, 영어, 특수문자 10글자이상
	private final String regexPattern2 = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{10,}$";

	public boolean checkValidationRegisterId(String targetString) {
		return Pattern.matches(regexPattern1, targetString);
	}

	public boolean checkValidationRegisterPassword(String targetString) {
		return Pattern.matches(regexPattern2, targetString);
	}
}

package com.ssafy.team8alette.domain.member.auth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.ssafy.team8alette.global.exception.MemberPasswordInvalidException;

@Component
public class PasswordUtil {

	public String encodePassword(String rawPassword) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(rawPassword.getBytes());

		StringBuilder builder = new StringBuilder();
		for (byte b : md.digest()) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

	public void match(String rawPassword, String targetPassword) throws NoSuchAlgorithmException {
		if (encodePassword(rawPassword).equals(targetPassword) != true) {
			throw new MemberPasswordInvalidException("비밀번호가 일치하지 않습니다");
		}
	}

	public String getRandomPassword() {
		return RandomStringUtils.random(12, 33, 125, true, true);
	}

}

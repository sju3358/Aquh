package com.ssafy.team8alette.member.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {

	public String encodePassword(String rawPassword) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(rawPassword.getBytes());

		StringBuilder builder = new StringBuilder();
		for (byte b : md.digest()) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

	public boolean match(String rawPassword, String targetPassword) throws NoSuchAlgorithmException {
		return encodePassword(rawPassword).equals(targetPassword);
	}
}
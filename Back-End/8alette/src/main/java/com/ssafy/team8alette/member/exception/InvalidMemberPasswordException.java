package com.ssafy.team8alette.member.exception;

public class InvalidMemberPasswordException extends RuntimeException {
	public InvalidMemberPasswordException() {
		super("비밀번호가 일치하지 않습니다.");
	}

	public InvalidMemberPasswordException(String message) {
		super(message);
	}
}


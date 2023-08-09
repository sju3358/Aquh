package com.ssafy.team8alette.domain.member.auth.exception;

public class MemberPasswordInvalidException extends RuntimeException {
	public MemberPasswordInvalidException() {
		super("비밀번호가 일치하지 않습니다.");
	}

	public MemberPasswordInvalidException(String message) {
		super(message);
	}
}


package com.ssafy.team8alette.member.exception;

public class UnAuthorizedException extends RuntimeException {

	public UnAuthorizedException() {
		super("로그인이 필요합니다.");
	}

	public UnAuthorizedException(String message) {
		super(message);
	}

}

package com.ssafy.team8alette.global.exception;

public class MemberNotExistException extends RuntimeException {

	public MemberNotExistException() {
		super("회원정보가 존재하지 않습니다.");
	}
}

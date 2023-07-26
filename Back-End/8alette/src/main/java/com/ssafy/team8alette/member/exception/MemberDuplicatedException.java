package com.ssafy.team8alette.member.exception;

public class MemberDuplicatedException extends RuntimeException {

	public MemberDuplicatedException() {
		super("등록된 이메일이  이미 존재합니다.");
	}
	
	public MemberDuplicatedException(String message) {
		super(message);
	}
}

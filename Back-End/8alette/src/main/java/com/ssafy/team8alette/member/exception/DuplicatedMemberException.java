package com.ssafy.team8alette.member.exception;

public class DuplicatedMemberException extends RuntimeException {

	public DuplicatedMemberException() {
		super("등록된 ID가 이미 존재합니다.");
	}

	public DuplicatedMemberException(String message) {
		super(message);
	}
}

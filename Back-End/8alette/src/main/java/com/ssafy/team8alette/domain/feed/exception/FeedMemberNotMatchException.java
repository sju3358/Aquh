package com.ssafy.team8alette.domain.feed.exception;

public class FeedMemberNotMatchException extends RuntimeException {

	public FeedMemberNotMatchException(String name) {
		super(name);
	}
}

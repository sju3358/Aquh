package com.ssafy.team8alette.domain.feed.method;

public class FeedMethod {

	public int levelCheck(int exp) {
		int level = 1;
		if (exp >= 1000 && exp < 2500) {
			level = 2;
		} else if (exp >= 2500 && exp < 4500) {
			level = 3;
		} else if (exp >= 4500 && exp < 7000) {
			level = 4;
		} else if (exp >= 7000 && exp < 10000) {
			level = 5;
		} else if (exp >= 10000) {
			level = 6;
		}
		return level;
	}
}

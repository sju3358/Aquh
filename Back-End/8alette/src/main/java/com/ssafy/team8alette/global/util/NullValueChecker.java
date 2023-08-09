package com.ssafy.team8alette.global.util;

import org.springframework.stereotype.Component;

import com.ssafy.team8alette.global.exception.NullValueException;

@Component
public class NullValueChecker {

	public void check(String... params) {
		for (String param : params) {
			if (param.equals("") == true) {
				throw new NullValueException("비어있는값이 존재합니다.");
			}
		}
	}
}

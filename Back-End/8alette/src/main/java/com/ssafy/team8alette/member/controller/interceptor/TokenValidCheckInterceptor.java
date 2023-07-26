package com.ssafy.team8alette.member.controller.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.team8alette.member.exception.UnAuthorizedException;
import com.ssafy.team8alette.member.util.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidCheckInterceptor implements HandlerInterceptor {
	private static final String HEADER_AUTH = "AUTH-TOKEN";

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public TokenValidCheckInterceptor(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		final String token = request.getHeader(HEADER_AUTH);

		if (token != null && jwtTokenProvider.checkToken(token)) {
			return true;
		} else {
			throw new UnAuthorizedException();
		}
	}
}

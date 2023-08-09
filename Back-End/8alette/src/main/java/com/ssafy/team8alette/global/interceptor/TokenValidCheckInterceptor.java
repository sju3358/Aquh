package com.ssafy.team8alette.global.interceptor;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.global.annotation.LoginRequired;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenValidCheckInterceptor implements HandlerInterceptor {
	private static final String HEADER_AUTH = "AUTH-TOKEN";

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// 개발용
		// return true;

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod)handler;
		LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
		if (Objects.isNull(loginRequired)) {
			return true;
		}

		final String token = request.getHeader(HEADER_AUTH);

		if (token != null) {
			jwtTokenProvider.checkToken(token);
			return true;
		} else {
			throw new UnAuthorizedException();
		}
	}
}

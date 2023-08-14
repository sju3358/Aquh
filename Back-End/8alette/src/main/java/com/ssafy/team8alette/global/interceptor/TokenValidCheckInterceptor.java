package com.ssafy.team8alette.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;

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

		return true;

		// if (!(handler instanceof HandlerMethod)) {
		// 	return true;
		// }
		//
		// HandlerMethod handlerMethod = (HandlerMethod)handler;
		// LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
		// if (Objects.isNull(loginRequired)) {
		// 	return true;
		// }
		//
		// final String token = request.getHeader(HEADER_AUTH);
		//
		// if (token != null) {
		// 	jwtTokenProvider.checkToken(token);
		// 	return true;
		// } else {
		// 	throw new UnAuthorizedException();
		// }
	}
}

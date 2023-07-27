package com.ssafy.team8alette.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.team8alette.common.interceptor.TokenValidCheckInterceptor;
import com.ssafy.team8alette.member.util.JwtTokenProvider;

@Component
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new TokenValidCheckInterceptor(new JwtTokenProvider()))
			.order(1)
			.addPathPatterns("api/v1/**");
	}
}
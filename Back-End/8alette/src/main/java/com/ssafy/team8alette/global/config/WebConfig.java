package com.ssafy.team8alette.global.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import com.ssafy.team8alette.global.interceptor.TokenValidCheckInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new TokenValidCheckInterceptor(new JwtTokenProvider()))
			.order(1)
			.addPathPatterns("/api/v1/**");
	}

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/api/v1/**")
			.allowedOrigins(
				"http://i9b108.p.ssafy.io:8080",
				"https://i9b108.p.ssafy.io:8080",
				"http://i9b108.p.ssafy.io:3000",
				"https://i9b108.p.ssafy.io:3000",
				"http://localhost:3000",
				"https://localhost:3000",
				"http://i9b108.p.ssafy.io:8080/",
				"https://i9b108.p.ssafy.io:8080/",
				"http://i9b108.p.ssafy.io:3000/",
				"https://i9b108.p.ssafy.io:3000/",
				"http://localhost:3000/",
				"https://localhost:3000/")
			.allowedMethods("GET", "POST", "PUT")
			.maxAge(3000);
	}
}
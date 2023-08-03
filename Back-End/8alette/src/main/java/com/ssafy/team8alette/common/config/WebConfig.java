package com.ssafy.team8alette.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
			.addPathPatterns("/api/v1/**");
	}

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/api/v1/**")
			.allowedOrigins("https://i9b108.p.ssafy.io/", "http://localhost:3000")
			.allowedMethods("GET", "POST", "PUT")
			.maxAge(3000);
	}
}
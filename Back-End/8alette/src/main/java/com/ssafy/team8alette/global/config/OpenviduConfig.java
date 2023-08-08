package com.ssafy.team8alette.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.openvidu.java.client.OpenVidu;

@Configuration
public class OpenviduConfig {

	@Value("${OPENVIDU_URL}")
	private String openviduUrl;
	@Value("${OPENVIDU_SECRET}")
	private String openviduSecret;

	@Bean
	public OpenVidu createOpenvidu() {
		OpenVidu openVidu = new OpenVidu(openviduUrl, openviduSecret);
		return openVidu;
	}
}

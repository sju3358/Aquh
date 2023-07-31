package com.ssafy.team8alette.common.config;

// 커넥션 설정 및 템플릿 구성
// @Configuration
public class RedisConfig {

	// @Value("${spring.data.redis.host}")
	// private String host;
	//
	// @Value("${spring.data.redis.port}")
	// private int port;
	//
	// // 커넥션 설정
	// @Bean
	// public RedisConnectionFactory redisConnectionFactory() {
	// 	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	// 	redisStandaloneConfiguration.setHostName(host);
	// 	redisStandaloneConfiguration.setPort(port);
	// 	return new LettuceConnectionFactory(redisStandaloneConfiguration);
	// }
	//
	// // @Bean
	// // public RedisTemplate<String, Object> redisTemplate() {
	// // 	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	// // 	redisTemplate.setConnectionFactory(redisConnectionFactory());
	// // 	redisTemplate.setKeySerializer(new StringRedisSerializer());
	// // 	redisTemplate.setValueSerializer(new StringRedisSerializer());
	// // 	return redisTemplate;
	// // }

}
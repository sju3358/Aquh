package com.ssafy.team8alette.domain.bubble.session.model.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "bubble_session")
public class BubbleSessionEntity extends StringRedisSerializer {

	@Id
	private String sessionId;

	private Session session;

	private Map<String, OpenViduRole> bubblers;
}

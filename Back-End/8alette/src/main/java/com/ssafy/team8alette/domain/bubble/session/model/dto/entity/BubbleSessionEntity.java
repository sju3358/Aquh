package com.ssafy.team8alette.domain.bubble.session.model.dto.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@RedisHash(value = "bubble_session")
public class BubbleSessionEntity {

	@Id
	private String sessionId;

	private Session session;

	private Map<String, OpenViduRole> bubblers;

}

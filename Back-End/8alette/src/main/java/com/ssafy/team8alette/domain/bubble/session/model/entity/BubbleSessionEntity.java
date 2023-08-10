package com.ssafy.team8alette.domain.bubble.session.model.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.ssafy.team8alette.domain.bubble.session.model.dto.BubbleSessionDto;

import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@RedisHash(value = "bubble_session")
public class BubbleSessionEntity {

	@Id
	private String sessionId;

	private Session session;

	private Map<String, OpenViduRole> bubblers;

	public BubbleSessionDto convertToDto() {
		return BubbleSessionDto.builder()
			.sessionId(this.sessionId)
			.session(this.session)
			.bubblers(this.bubblers)
			.build();
	}
}

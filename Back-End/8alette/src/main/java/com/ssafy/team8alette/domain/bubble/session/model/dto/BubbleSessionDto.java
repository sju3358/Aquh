package com.ssafy.team8alette.domain.bubble.session.model.dto;

import java.util.Map;

import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BubbleSessionDto {

	private String sessionId;

	private Session session;

	private Map<String, OpenViduRole> bubblers;
}

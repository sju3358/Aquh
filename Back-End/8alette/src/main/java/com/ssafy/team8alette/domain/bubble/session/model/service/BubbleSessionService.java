package com.ssafy.team8alette.domain.bubble.session.model.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.SessionNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleSessionRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleSessionEntity;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BubbleSessionService {

	private final BubbleSessionRepository bubbleSessionRepository;
	private final OpenVidu openVidu;

	public String createHostBubbleSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {

		String serverData = "{\"serverData\": \"" + sessionId + "\"}";
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(serverData)
			.role(OpenViduRole.PUBLISHER)
			.build();

		Session session = openVidu.createSession();
		String token = session.createConnection(connectionProperties).getToken();

		Map<String, OpenViduRole> bubblers = new ConcurrentHashMap<>();
		bubblers.put(token, OpenViduRole.PUBLISHER);

		BubbleSessionEntity bubbleSession = new BubbleSessionEntity();

		bubbleSession.setSessionId(sessionId);
		bubbleSession.setSession(session);
		bubbleSession.setBubblers(bubblers);

		bubbleSessionRepository.save(bubbleSession);

		return token;
	}

	public String createSubBubbleSession(String sessionId) throws
		OpenViduJavaClientException,
		OpenViduHttpException {
		BubbleSessionEntity bubbleSession = bubbleSessionRepository.findBubbleSessionEntityBySessionId(sessionId)
			.orElseThrow(() -> new SessionNotFoundException());

		String serverData = "{\"serverData\": \"" + sessionId + "\"}";
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(serverData)
			.role(OpenViduRole.SUBSCRIBER)
			.build();

		String token = bubbleSession.getSession().createConnection(connectionProperties).getToken();

		bubbleSession.getBubblers().put(token, OpenViduRole.SUBSCRIBER);

		return token;
	}
}

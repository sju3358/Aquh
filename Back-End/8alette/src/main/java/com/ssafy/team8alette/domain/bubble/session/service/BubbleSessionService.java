package com.ssafy.team8alette.domain.bubble.session.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.SessionNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleSessionEntity;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleSessionRepository;
import com.ssafy.team8alette.global.exception.UnAuthorizedException;

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

	public String createHostBubbleSession(String sessionId, Long memberNumber) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		sessionId = sessionId.trim();

		System.out.println("세션 생성 sesson ID : " + sessionId + "memberNumber : " + memberNumber);
		String serverData = "{\"serverData\": \"" + memberNumber + "\"}";
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(serverData)
			.role(OpenViduRole.PUBLISHER)
			.build();

		Session session = openVidu.createSession();
		String token = session.createConnection(connectionProperties).getToken();

		Map<String, OpenViduRole> bubblers = new ConcurrentHashMap<>();
		bubblers.put(token, OpenViduRole.PUBLISHER);

		BubbleSessionEntity bubbleSession = BubbleSessionEntity.builder()
			.sessionId(sessionId)
			.session(session)
			.bubblers(bubblers)
			.build();

		bubbleSessionRepository.saveBubbleSession(sessionId, bubbleSession);

		return token;
	}

	public String createSubBubbleSession(String sessionId, Long memberNumber) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		sessionId = sessionId.trim();
		
		System.out.println("세션 참여 sesson ID : " + sessionId + "memberNumber : " + memberNumber);
		BubbleSessionEntity bubbleSession = bubbleSessionRepository.findBubbleSessionEntityBySessionId(sessionId)
			.orElseThrow(() -> new SessionNotFoundException());

		String serverData = "{\"serverData\": \"" + memberNumber + "\"}";
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(serverData)
			.role(OpenViduRole.PUBLISHER)
			.build();

		String token = bubbleSession.getSession().createConnection(connectionProperties).getToken();

		bubbleSession.getBubblers().put(token, OpenViduRole.PUBLISHER);

		return token;
	}

	public void leaveBubbleSession(Long bubbleNumber, String token) {

		String sessionId = Long.toString(bubbleNumber);

		BubbleSessionEntity bubbleSession = bubbleSessionRepository.findBubbleSessionEntityBySessionId(sessionId)
			.orElseThrow(() -> new SessionNotFoundException());

		bubbleSession.getBubblers().remove(token);

		bubbleSessionRepository.saveBubbleSession(sessionId, bubbleSession);
	}

	public void deleteBubbleSession(String sessionId, String token) {

		BubbleSessionEntity bubbleSession = bubbleSessionRepository.findBubbleSessionEntityBySessionId(sessionId)
			.orElseThrow(() -> new SessionNotFoundException());

		if (bubbleSession.getBubblers().get(token) != OpenViduRole.PUBLISHER)
			throw new UnAuthorizedException();

		bubbleSessionRepository.saveBubbleSession(sessionId, bubbleSession);
	}

}

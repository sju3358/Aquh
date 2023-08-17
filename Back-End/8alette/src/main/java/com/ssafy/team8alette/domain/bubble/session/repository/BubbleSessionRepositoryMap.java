package com.ssafy.team8alette.domain.bubble.session.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleSessionEntity;

@Primary
@Repository
public class BubbleSessionRepositoryMap implements BubbleSessionRepository {

	private final static Map<String, BubbleSessionEntity> bubbleSessions = new ConcurrentHashMap<>();

	public void saveBubbleSession(String sessionId, BubbleSessionEntity bubbleSession) {
		bubbleSessions.put(sessionId, bubbleSession);
	}

	public Optional<BubbleSessionEntity> findBubbleSessionEntityBySessionId(String sessionId) {
		return Optional.ofNullable(bubbleSessions.get(sessionId));
	}

	public void deleteBubbleSession(String sessionId) {
		bubbleSessions.remove(sessionId);
	}

}

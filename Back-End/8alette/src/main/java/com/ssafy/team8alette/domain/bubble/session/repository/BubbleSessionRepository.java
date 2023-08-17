package com.ssafy.team8alette.domain.bubble.session.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleSessionEntity;

@Repository
public interface BubbleSessionRepository {

	void saveBubbleSession(String sessionId, BubbleSessionEntity bubbleSession);

	Optional<BubbleSessionEntity> findBubbleSessionEntityBySessionId(String sessionId);

	void deleteBubbleSession(String sessionId);
}

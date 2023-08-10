package com.ssafy.team8alette.domain.bubble.session.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleSessionEntity;

public interface BubbleSessionRepository extends CrudRepository<BubbleSessionEntity, String> {

	Optional<BubbleSessionEntity> findBubbleSessionEntityBySessionId(String sessionId);
}

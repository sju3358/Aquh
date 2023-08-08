package com.ssafy.team8alette.domain.bubble.session.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleSessionEntity;

public interface BubbleSessionRepository extends CrudRepository<BubbleSessionEntity, String> {
}

package com.ssafy.team8alette.domain.bubble.session.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleSessionEntity;

@Repository
public interface BubbleSessionRepositoryRedis
	extends BubbleSessionRepository, CrudRepository<String, BubbleSessionEntity> {

}

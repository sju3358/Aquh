package com.ssafy.team8alette.domain.bubble.session.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;

public interface BubbleRepository extends JpaRepository<BubbleEntity, Long> {

	Optional<BubbleEntity> findBubbleEntityByBubbleNumber(Long bubbleNumber);

	List<BubbleEntity> findBubbleEntitiesByBubbleTypeIsTrue();

	List<BubbleEntity> findBubbleEntitiesByBubbleTypeIsFalse();
}

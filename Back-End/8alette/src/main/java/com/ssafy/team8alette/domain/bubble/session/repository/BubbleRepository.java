package com.ssafy.team8alette.domain.bubble.session.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;

public interface BubbleRepository extends JpaRepository<BubbleEntity, Long> {

	Optional<BubbleEntity> findBubbleEntityByBubbleNumber(Long bubbleNumber);

	Optional<List<BubbleEntity>> findBubbleEntitiesByBubbleTypeIsTrue();

	Optional<List<BubbleEntity>> findBubbleEntitiesByBubbleTypeIsFalse();

}

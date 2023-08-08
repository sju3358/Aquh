package com.ssafy.team8alette.domain.bubble.session.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;

public interface BubbleRepository extends JpaRepository<BubbleEntity, Long> {

	Optional<BubbleEntity> findBubbleEntityByBubbleNumber(Long bubbleNumber);
}

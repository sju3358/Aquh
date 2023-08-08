package com.ssafy.team8alette.domain.bubble.session.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;

@Repository
public interface BubbleRepository extends JpaRepository<BubbleEntity, Long> {
	
}

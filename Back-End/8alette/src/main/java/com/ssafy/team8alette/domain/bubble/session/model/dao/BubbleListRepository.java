package com.ssafy.team8alette.domain.bubble.session.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleListEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.key.GroupID;

public interface BubbleListRepository extends JpaRepository<BubbleListEntity, GroupID> {
}

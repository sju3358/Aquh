package com.ssafy.team8alette.domain.bubble.session.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleParticipantEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.key.GroupID;

public interface BubbleListRepository extends JpaRepository<BubbleParticipantEntity, GroupID> {

	Optional<BubbleParticipantEntity> findBubbleListEntitiesByGroupID(GroupID groupID);
}

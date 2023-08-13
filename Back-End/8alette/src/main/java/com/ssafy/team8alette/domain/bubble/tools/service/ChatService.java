package com.ssafy.team8alette.domain.bubble.tools.service;

import com.ssafy.team8alette.domain.bubble.session.model.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.repository.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.VoteQuestionRepository;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.key.VoteID;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.request.VoteQuestionRequestDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.VoteQuestionResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteQuestionEntity;
import com.ssafy.team8alette.domain.bubble.tools.model.entity.VoteSelectEntity;
import com.ssafy.team8alette.domain.bubble.tools.repository.ChatRepository;
import com.ssafy.team8alette.domain.bubble.tools.repository.VoteSelectrRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatRepository chatRepository;


}

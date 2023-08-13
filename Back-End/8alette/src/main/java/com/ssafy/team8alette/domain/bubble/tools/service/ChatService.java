package com.ssafy.team8alette.domain.bubble.tools.service;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.ChatResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.repository.ChatRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Flux<ChatResponseDTO> getChatLog(Long bubble_number) {
        return chatRepository.mFindByBubbleNumber(bubble_number)
                .subscribeOn(Schedulers.boundedElastic());
    }

	public Mono<ChatResponseDTO> insertChat(ChatResponseDTO chatResponseDTO, Long member_number) {
        chatResponseDTO.setSender(member_number);
		chatResponseDTO.setCreatedAt(LocalDateTime.now());
		return chatRepository.save(chatResponseDTO);
	}
}

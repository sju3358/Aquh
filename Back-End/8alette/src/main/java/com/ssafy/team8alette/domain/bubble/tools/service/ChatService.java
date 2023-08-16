package com.ssafy.team8alette.domain.bubble.tools.service;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.Chat;
import com.ssafy.team8alette.domain.bubble.tools.repository.ChatRepository;
import com.ssafy.team8alette.domain.member.record.model.service.MemberRecordService;

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
    private final MemberRecordService memberRecordService;

    public Flux<Chat> getChatLog(Long bubble_number) {
        return chatRepository.mFindByBubbleNumber(bubble_number)
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Chat> insertChat(Chat chat, Long member_number) {
        chat.setSender(member_number);
        chat.setCreatedAt(LocalDateTime.now());
        chat.setLevel(memberRecordService.getMemberLevel(member_number));

        return chatRepository.save(chat);
    }
}

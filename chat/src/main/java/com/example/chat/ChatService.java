package com.example.chat;

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

    public Flux<Chat> getChatLog(Long bubble_number) {
        return chatRepository.mFindByBubbleNum(bubble_number)
                .subscribeOn(Schedulers.boundedElastic());
    }

	public Mono<Chat> insertChat(Chat chat, Long member_number) {
        chat.setSender(member_number);
        chat.setCreatedAt(LocalDateTime.now());
		return chatRepository.save(chat);
	}
}

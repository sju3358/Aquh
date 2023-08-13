package com.ssafy.team8alette.domain.bubble.tools.controller;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.ChatResponseDTO;
import com.ssafy.team8alette.domain.bubble.tools.repository.ChatRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bubble/Chat")
public class ChatController {
    private final ChatRepository chatRepository;

    // 귓속말
    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponseDTO> getMsg(
            @PathVariable String sender,
            @PathVariable String receiver
    ) {
        return chatRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponseDTO> findByRoomNum(
            @PathVariable Long roomNum
    ) {
        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @PostMapping("/chat")
    public Mono<ChatResponseDTO> setMsg(@RequestBody ChatResponseDTO chatResponseDTO) {
        chatResponseDTO.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chatResponseDTO);
    }
}

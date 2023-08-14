package com.example.chat;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatService chatService;

    // 귓속말
//    @CrossOrigin
//    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Chat> getMsg(
//            @PathVariable String sender,
//            @PathVariable String receiver
//    ) {
//        return chatRepository.mFindBySender(sender, receiver)
//                .subscribeOn(Schedulers.boundedElastic());
//    }

    @CrossOrigin
    @GetMapping(value = "/chat/{bubbleNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomNum(
            @PathVariable Long bubbleNum
    ) {
        return chatService.getChatLog(bubbleNum);
    }

    @CrossOrigin
    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatService.insertChat(chat, 1L);
    }
}

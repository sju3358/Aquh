package com.ssafy.team8alette.domain.bubble.tools.controller;

import com.ssafy.team8alette.domain.bubble.tools.model.dto.response.Chat;
import com.ssafy.team8alette.domain.bubble.tools.service.ChatService;
import com.ssafy.team8alette.domain.member.auth.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bubble/chat")
public class ChatController {
    private final ChatService chatService;
    private final JwtTokenProvider jwtTokenProvider;

    // 귓속말
//    @CrossOrigin
//    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<ChatResponseDTO> getMsg(
//            @PathVariable String sender,
//            @PathVariable String receiver
//    ) {
//        return chatRepository.mFindBySender(sender, receiver)
//                .subscribeOn(Schedulers.boundedElastic());
//    }

    @CrossOrigin
    @GetMapping(value = "/{bubble_number}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getAllChatLog(
            @PathVariable Long bubble_number) {
        return chatService.getChatLog(bubble_number);
    }

    @CrossOrigin
    @PostMapping
    public Mono<Chat> setMsg(
            @RequestHeader(value = "AUTH-TOKEN") String jwtToken,
            @RequestBody Chat chat) throws ParseException {

        Long member_number = jwtTokenProvider.getMemberNumber(jwtToken);

        return chatService.insertChat(chat, member_number);
    }
}

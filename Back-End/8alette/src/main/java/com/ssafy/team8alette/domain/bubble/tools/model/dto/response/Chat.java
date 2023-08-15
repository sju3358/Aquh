package com.ssafy.team8alette.domain.bubble.tools.model.dto.response;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chat")
public class Chat {
    @Id
    private String id;
//    private Long receiver; // 받는 사람 (귓속말)
    
    private Long sender; // 보내는 사람 회원 번호
    
    private String nickName; // 보내는 사람 닉네임
    
    private String msg; // 메세지
    
    private Long bubbleNumber; // 방 번호
    
    private LocalDateTime createdAt;
}

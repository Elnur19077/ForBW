package bw.black.controller;

import bw.black.dto.request.ChatMessageRequest;
import bw.black.dto.response.ChatMessageResponse;
import bw.black.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final ChatMessageService service;

    public ChatController(@Qualifier("chatMessageServiceImpl") ChatMessageService service) {
        this.service = service;
    }

    @MessageMapping("/send") // /app/send
    @SendTo("/topic/messages")
    public ChatMessageResponse sendMessage(ChatMessageRequest message) {
        return service.save(message);
    }
}

package bw.black.controller;

import bw.black.dto.request.ChatMessageRequest;
import bw.black.dto.response.ChatMessageResponse;
import bw.black.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {
    private final ChatMessageService service;

    public ChatRestController(@Qualifier("chatMessageServiceImpl") ChatMessageService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChatMessageResponse> getAll() {
        return service.getAllMessages();
    }

    @PostMapping
    public ChatMessageResponse sendMessage(@RequestBody ChatMessageRequest request) {
        return service.save(request);
    }
}

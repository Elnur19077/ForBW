package bw.black.service;

import bw.black.dto.request.ChatMessageRequest;
import bw.black.dto.response.ChatMessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatMessageService {

    ChatMessageResponse save(ChatMessageRequest request);
    List<ChatMessageResponse> getAllMessages();
}

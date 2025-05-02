package bw.black.service.impl;

import bw.black.dto.request.ChatMessageRequest;
import bw.black.dto.response.ChatMessageResponse;
import bw.black.entity.ChatMessage;
import bw.black.repository.ChatMessageRepository;
import bw.black.service.ChatMessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository repository;

    public ChatMessageServiceImpl(ChatMessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public ChatMessageResponse save(ChatMessageRequest request) {
        ChatMessage message = new ChatMessage();
        message.setSender(request.getSender());
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());

        ChatMessage saved = repository.save(message);

        return toResponse(saved);
    }

    @Override
    public List<ChatMessageResponse> getAllMessages() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ChatMessageResponse toResponse(ChatMessage message) {
        ChatMessageResponse response = new ChatMessageResponse();
        response.setId(message.getId());
        response.setSender(message.getSender());
        response.setContent(message.getContent());
        response.setTimestamp(message.getTimestamp());
        return response;
    }
}

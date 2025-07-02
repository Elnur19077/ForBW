package bw.black.controller;

import bw.black.entity.EmailLog;
import bw.black.repository.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/email-logs")
@RequiredArgsConstructor
public class EmailLogController {
    private final EmailLogRepository emailLogRepository;

    @GetMapping
    public List<EmailLog> getAllLogs() {
        return emailLogRepository.findAll(Sort.by(Sort.Direction.DESC, "sentAt"));
    }
}

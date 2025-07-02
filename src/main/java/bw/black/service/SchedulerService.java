package bw.black.service;

import bw.black.entity.EmailTemplate;
import bw.black.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final EmailTemplateRepository templateRepository;
    private final EmailService mailService;


    @Scheduled(cron = "0 0 9 1 * ?") // Hər ayın 1-i saat 09:00
    public void sendMonthlyEmails() {
        List<EmailTemplate> templates = templateRepository.findByActiveTrue();

        for (EmailTemplate template : templates) {
            mailService.sendAndLogEmail(
                    template.getRecipient(),
                    template.getSubject(),
                    template.getMessage()
            );
        }
    }
}


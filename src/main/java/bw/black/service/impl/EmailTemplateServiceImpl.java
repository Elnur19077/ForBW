package bw.black.service.impl;

import bw.black.dto.request.EmailTemplateRequest;
import bw.black.dto.response.EmailTemplateResponse;
import bw.black.entity.EmailTemplate;
import bw.black.repository.EmailTemplateRepository;
import bw.black.service.EmailTemplateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class EmailTemplateServiceImpl  implements EmailTemplateService {

    private final EmailTemplateRepository templateRepository;

    @Override
    public EmailTemplateResponse createTemplate(EmailTemplateRequest request) {
        EmailTemplate template = EmailTemplate.builder()
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .message(request.getMessage())
                .active(request.isActive())
                .build();
        return mapToResponse(templateRepository.save(template));
    }

    @Override
    public List<EmailTemplateResponse> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EmailTemplateResponse updateTemplate(Long id, EmailTemplateRequest request) {
        EmailTemplate template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with id " + id));

        template.setRecipient(request.getRecipient());
        template.setSubject(request.getSubject());
        template.setMessage(request.getMessage());
        template.setActive(request.isActive());

        return mapToResponse(templateRepository.save(template));
    }

    @Override
    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    private EmailTemplateResponse mapToResponse(EmailTemplate template) {
        return EmailTemplateResponse.builder()
                .id(template.getId())
                .recipient(template.getRecipient())
                .subject(template.getSubject())
                .message(template.getMessage())
                .active(template.isActive())
                .build();
    }
}

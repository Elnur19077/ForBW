package bw.black.service;

import bw.black.dto.request.EmailTemplateRequest;
import bw.black.dto.response.EmailTemplateResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmailTemplateService {
    EmailTemplateResponse createTemplate(EmailTemplateRequest request);

    List<EmailTemplateResponse> getAllTemplates();

    EmailTemplateResponse updateTemplate(Long id, EmailTemplateRequest request);

    void deleteTemplate(Long id);
}

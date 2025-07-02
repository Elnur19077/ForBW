package bw.black.service;

import bw.black.entity.Contacts;
import bw.black.entity.EmailLog;
import bw.black.repository.EmailLogRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;

    public void sendContactPdfByEmail(String toEmail, Contacts contact, ByteArrayInputStream pdfStream) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Əlaqə məlumatları - PDF");
            helper.setText("Zəhmət olmasa əlavə olunan PDF faylı yoxlayın.", false);

            // PDF faylı əlavə et
            helper.addAttachment("contact-info.pdf", new ByteArrayResource(pdfStream.readAllBytes()));

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendPoPdfByEmail(String toEmail, String companyName, String uploadedBy, String fileName, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Yeni PO sənədi - " + companyName);
            helper.setText("Salam,\n\nYeni PO sənədi yerləşdirildi.\n" +
                    "Şirkət: " + companyName + "\n" +
                    "Yerləşdirən: " + uploadedBy + "\n" +
                    "Zəhmət olmasa əlavə olunmuş sənədi yoxlayın.", false);

            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendAndLogEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        EmailLog log = EmailLog.builder()
                .recipient(to)
                .subject(subject)
                .message(text)
                .sentAt(LocalDateTime.now())
                .build();
        emailLogRepository.save(log);
    }
}

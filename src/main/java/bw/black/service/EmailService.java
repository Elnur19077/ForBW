package bw.black.service;

import bw.black.entity.Contacts;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

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
}

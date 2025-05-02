package bw.black.controller;

import bw.black.entity.Contacts;
import bw.black.repository.ContactsRepository;
import bw.black.service.EmailService;
import bw.black.service.PdfService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final ContactsRepository contactsRepository;
    private final PdfService pdfService;
    private final EmailService emailService;

    public PdfController(ContactsRepository contactsRepository, PdfService pdfService, EmailService emailService) {
        this.contactsRepository = contactsRepository;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/contact/{id}")
    public ResponseEntity<byte[]> generateContactPdf(@PathVariable Long id) {
        Contacts contact = contactsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Əlaqə tapılmadı: " + id));

        ByteArrayInputStream pdfStream = pdfService.generateContactsPdf(contact);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contact_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.readAllBytes());
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/send-pdf")
    public ResponseEntity<String> sendPdfToEmail(@PathVariable Long id, @RequestParam String email) {
        Contacts contact = contactsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Əlaqə tapılmadı"));

        ByteArrayInputStream pdf = pdfService.generateContactsPdf(contact);
        emailService.sendContactPdfByEmail(email, contact, pdf);

        return ResponseEntity.ok("PDF mailə göndərildi.");
    }
}
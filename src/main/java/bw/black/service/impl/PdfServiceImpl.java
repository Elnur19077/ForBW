package bw.black.service.impl;

import bw.black.entity.Contacts;
import bw.black.repository.ContactsRepository;
import bw.black.service.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@Transactional
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
    private final ContactsRepository contactsRepository;

    @Override
    public ByteArrayInputStream generateContactsPdf(Contacts contact) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font tableBodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Başlıq
            Paragraph title = new Paragraph("ƏLAQƏ MƏLUMATLARI", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // 2 sütunlu cədvəl
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Data əlavə edən util method
            addRow(table, "Şirkət Adı", contact.getCompanyName(), tableHeaderFont, tableBodyFont);
            addRow(table, "Email", contact.getEmail(), tableHeaderFont, tableBodyFont);
            addRow(table, "Ünvan", contact.getAddress(), tableHeaderFont, tableBodyFont);
            addRow(table, "Mobil", contact.getMobile(), tableHeaderFont, tableBodyFont);
            addRow(table, "Telefon", contact.getPhone(), tableHeaderFont, tableBodyFont);
            addRow(table, "Veb sayt", contact.getWebsite(), tableHeaderFont, tableBodyFont);
            addRow(table, "Məhsul çeşidi", contact.getProductRange(), tableHeaderFont, tableBodyFont);
            addRow(table, "Brend", contact.getBrand(), tableHeaderFont, tableBodyFont);
            addRow(table, "Pay Sahibləri", contact.getStakeHolders(), tableHeaderFont, tableBodyFont);

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    private void addRow(PdfPTable table, String key, String value, Font headerFont, Font bodyFont) {
        table.addCell(new Phrase(key, headerFont));
        table.addCell(new Phrase(value != null ? value : "", bodyFont));
    }







}

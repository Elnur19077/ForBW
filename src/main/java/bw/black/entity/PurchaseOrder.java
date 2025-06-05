package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "purchase_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "po_seq_gen")
    @SequenceGenerator(name = "po_seq_gen", sequenceName = "po_seq", allocationSize = 1)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Column(name = "accountant_email")
    private String accountantEmail;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;
}

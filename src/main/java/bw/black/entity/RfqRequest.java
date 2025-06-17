package bw.black.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RFQ")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RfqRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestName;
    private String companyName;
    private LocalDateTime deadline;
    private boolean submitted;
    private String note;
    private String salesperson;
    private String pdfPath;
    private String excelPath;
}

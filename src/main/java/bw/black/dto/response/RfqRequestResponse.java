package bw.black.dto.response;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RfqRequestResponse {
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

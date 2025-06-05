package bw.black.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PurchaseOrderResponse {
    private Long id;
    private String fileName;
    private String companyName;
    private LocalDate uploadDate;
    private String uploadedBy;
}

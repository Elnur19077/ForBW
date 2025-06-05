package bw.black.dto.request;

import lombok.Data;

@Data
public class PurchaseOrderRequest {
    private String companyName;
    private String uploadedBy;
    private String accountantEmail;
}

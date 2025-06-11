package bw.black.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SingleProductResponse {
    private String hsCode;
    private Double unitPrice;
    private Double customsFee;
    private Double totalCost;
    private Double finalPrice;
    private Double vatAmount;
    private Double grossProfit;
    private Double netProfit;
}

package bw.black.dto.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class LukoilProductResponse {
    private String productName;
    private double totalCostWithoutMargin;
    private double internalSalesCost;
    private double grossMargin;
    private double firstNetMargin;
    private double secondNetMargin;
    private double finalNetMargin;
    private double lukoilSalesPrice;
    private double netProfit;
}

package bw.black.dto.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class UaeServiceProductResponse {
    private String productName;
    private double totalCostWithoutMargin;
    private double finalSalesPrice;
    private double grossProfit;
    private double netProfit;
    private double profitAfterBonuses;
    private double monthlyCoverage;
    private double annualCoverage;
}

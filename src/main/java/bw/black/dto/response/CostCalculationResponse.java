package bw.black.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CostCalculationResponse {
    private List<SingleProductResponse> products;

    private Double totalFinalPrice;
    private Double totalNetProfit;
    private Double totalEmployeeBonus;
    private Double totalCustomerBonus;
    private Double totalBankGuaranteeAmount;

    private Double yearlyCoveragePercentage;
    private Double monthlyCoveragePercentage;
    private Double turnoverCoveragePercentage;

}

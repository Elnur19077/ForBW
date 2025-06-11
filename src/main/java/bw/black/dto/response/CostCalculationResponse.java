package bw.black.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CostCalculationResponse {
    private Double unitPrice;
    private Double finalPrice;
    private Double netProfit;
    private Double employeeBonus;
    private Double customerBonus;
    private Double yearlyCoveragePercentage;
    private Double monthlyCoveragePercentage;
    private Double turnoverCoveragePercentage;
    private Double bankGuaranteeAmount;

}

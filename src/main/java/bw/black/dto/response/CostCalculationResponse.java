package bw.black.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CostCalculationResponse {
    private Double finalPrice;
    private Double netProfit;
    private Double yearlyCoveragePercentage;   // Yeni
    private Double monthlyCoveragePercentage;
}

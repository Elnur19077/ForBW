package bw.black.dto.request;

import lombok.Data;

@Data
public class CostCalculationRequest {
    private Double basePrice;
    private Double transportCost;
    private Double customsDuty;
    private Double brokerCost;           // optional, default 200
    private Double antiMonopolyFee;      // optional, default 100
    private Double profitPercentage;
    // 0, 5, 15 (%)

}
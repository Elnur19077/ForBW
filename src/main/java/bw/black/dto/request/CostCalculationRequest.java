package bw.black.dto.request;

import lombok.Data;

@Data
public class CostCalculationRequest {
    private Double basePrice;
    private Integer unitCount;
    private String hsCode;

    private Double transportCost;
    private Double customsDuty;
    private Double brokerCost;
    private Double antiMonopolyFee;

    private Double profitPercentage;
    private Double employeeBonusPercent;
    private Double customerBonusPercent;
    private Integer guaranteeMonths;

}
package bw.black.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CostCalculationRequest {
    private List<ProductRequest> products;

    private Double transportCost;
    private Double customsDuty; // Əgər fərdi hsCodeDuty verilməyibsə istifadə olunur
    private Double brokerCost;
    private Double antiMonopolyFee;

    private Double profitPercentage;
    private Double employeeBonusPercent;
    private Double customerBonusPercent;
    private Integer guaranteeMonths;

}
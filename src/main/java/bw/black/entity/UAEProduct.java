package bw.black.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "uae_cost_calculation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UAEProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rfqNo;
    private String clientName;
    private String productName;
    private double unitCount;
    private double goodsPrice;
    private double transportCost;
    private double otherCost;
    private double warehouseCost;
    private double marginPercent;
    private double employeeBonus;
    private double customerBonus;
    private double totalCostWithoutMargin;
    private double finalSalesPrice;
    private double grossProfit;
    private double netProfit;
    private double profitAfterBonuses;
    private double monthlyCoverage;
    private double annualCoverage;
    private LocalDateTime createdAt;
}

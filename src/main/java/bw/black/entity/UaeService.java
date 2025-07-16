package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "uae_service")
@Setter
@Getter
@DynamicInsert
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UaeService {
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
}

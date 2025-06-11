package bw.black.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COST_CALCULATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class CostCalculation {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cost_seq")
        @SequenceGenerator(name = "cost_seq", sequenceName = "COST_SEQ", allocationSize = 1)
        private Long id;

        // Inputlar
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

        // Hesablananlar
        private Double unitPrice;
        private Double customsFee;
        private Double totalCost;
        private Double finalPrice;
        private Double vatAmount;
        private Double bankGuarantee;
        private Double grossProfit;
        private Double netProfit;
        private Double employeeBonus;
        private Double customerBonus;

        // Əhatə göstəriciləri
        private Double yearlyCoveragePercentage;
        private Double monthlyCoveragePercentage;
        private Double turnoverCoveragePercentage;
    }



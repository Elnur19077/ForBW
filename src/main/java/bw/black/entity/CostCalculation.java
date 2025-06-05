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
    private Double basePrice;        // Malın ana qiyməti
    private Double transportCost;    // Yol pulu
    private Double customsDuty;      // Gömrük rüsumu (%)
    private Double brokerCost;       // Default 200
    private Double antiMonopolyFee;  // Default 100
    private Double profitPercentage; // 25%
    private Double finalPrice;       // Satış qiyməti
    private Double netProfit;        // Net mənfəət
}

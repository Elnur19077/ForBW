package bw.black.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "lukoilcost")
@Setter
@Getter
@DynamicInsert
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LukoilCostCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rfqNo;
    private String clientName;
    private String productName;
    private double goods;
    private double unit;
    private double transportToFCA;
    private double transportToDDP;
    private double otherCost;
    private double totalCostWithoutMargin;
    private double internalSalesCost;
    private double grossMargin;
    private double firstNetMargin;
    private double secondNetMargin;
    private double finalNetMargin;
    private double lukoilSalesPrice;
    private double netProfit;

    private LocalDateTime createdAt;
}

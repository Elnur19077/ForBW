package bw.black.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class UaeServiceResponse {
    private String rfqNo;
    private String clientName;
    private List<UaeServiceProductResponse> products;
    private double totalNetProfit;
    private double totalGrossProfit;
    private double totalFinalSales;
    private double totalMonthlyCoverage;
    private double totalAnnualCoverage;
}

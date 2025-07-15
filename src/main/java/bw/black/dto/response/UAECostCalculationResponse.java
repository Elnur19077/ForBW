package bw.black.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class UAECostCalculationResponse {
    private String rfqNo;
    private String clientName;
    private List<UAEProductResponse> products;
    private double totalNetProfit;

}

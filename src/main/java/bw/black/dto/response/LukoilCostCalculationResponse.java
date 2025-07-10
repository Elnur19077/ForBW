package bw.black.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class LukoilCostCalculationResponse {

    private String rfqNo;
    private String clientName;
    private List<LukoilProductResponse> products;
    private double totalNetProfit;
}

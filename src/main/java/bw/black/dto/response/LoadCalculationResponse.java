package bw.black.dto.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
public class LoadCalculationResponse {
    private String zoneName;
    private String country;
    private double weight;
    private double price;
}

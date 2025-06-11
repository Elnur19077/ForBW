package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductRequest {
    private Double basePrice;
    private Integer unitCount;
    private String hsCode;
    private Double hsCodeDuty;
    private Double antiMonopolyFee; // fərdi gömrük faizi
}

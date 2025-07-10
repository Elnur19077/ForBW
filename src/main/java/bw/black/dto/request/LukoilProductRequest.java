package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LukoilProductRequest {
    private String productName;
    private double unit;
    private double goods;
    private double transportToFCA;
    private double transportToDDP;
    private double otherCost;
    private double marginPercent;
}

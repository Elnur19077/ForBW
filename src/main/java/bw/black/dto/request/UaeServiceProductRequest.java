package bw.black.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UaeServiceProductRequest {
    private String productName;
    private double unitCount;
    private double goodsPrice;
    private double transportCost;
    private double otherCost;
    private double warehouseCost;
    private double marginPercent;
    private double employeeBonus;
    private double customerBonus;
}

package bw.black.service;

import bw.black.dto.request.LukoilCostCalculationRequest;
import bw.black.dto.response.CostCalculationResponse;
import bw.black.dto.response.LukoilCostCalculationResponse;
import bw.black.entity.LukoilCostCalculation;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface LukoilCostCalculationService {

    LukoilCostCalculationResponse calculate(LukoilCostCalculationRequest request);
    LukoilCostCalculationResponse submit(LukoilCostCalculationRequest request);
    List<LukoilCostCalculation> getAllCalculations();
    void deleteCalculation(Long id);
    List<LukoilCostCalculation> searchByProductName(String keyword);
}

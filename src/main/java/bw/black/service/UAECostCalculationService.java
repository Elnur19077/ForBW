package bw.black.service;

import bw.black.dto.request.UAECostCalculationRequest;
import bw.black.dto.response.UAECostCalculationResponse;
import bw.black.entity.UAEProduct;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UAECostCalculationService {
    UAECostCalculationResponse calculate(UAECostCalculationRequest request);
    UAECostCalculationResponse submit(UAECostCalculationRequest request);
    List<UAEProduct> getAll();
    void delete(Long id);
    List<UAEProduct> search(String keyword);
}

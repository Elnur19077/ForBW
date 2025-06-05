package bw.black.service;

import bw.black.dto.request.CostCalculationRequest;
import bw.black.dto.response.CostCalculationResponse;
import org.springframework.stereotype.Service;

@Service
public interface CostCalculationService {
    CostCalculationResponse calculate(CostCalculationRequest request);

}

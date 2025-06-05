package bw.black.service;

import bw.black.dto.request.LoadCalculationRequest;
import bw.black.dto.response.LoadCalculationResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoadCalculationService {
    LoadCalculationResponse calculatePrice(LoadCalculationRequest request);

}

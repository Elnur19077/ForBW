package bw.black.controller;

import bw.black.dto.request.CostCalculationRequest;
import bw.black.dto.response.CostCalculationResponse;
import bw.black.service.CostCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cost")
@RequiredArgsConstructor
public class CostCalculationController {
    @Qualifier("costCalculationService")
    private final CostCalculationService service;

    @PostMapping("/calculate")
    public ResponseEntity<CostCalculationResponse> calculate(@RequestBody CostCalculationRequest request) {
        CostCalculationResponse response = service.calculate(request);
        return ResponseEntity.ok(response);
    }
}

package bw.black.controller;

import bw.black.dto.request.LoadCalculationRequest;
import bw.black.dto.response.LoadCalculationResponse;
import bw.black.service.LoadCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/load")
@RequiredArgsConstructor
public class LoadCalculationController {

    private final LoadCalculationService loadCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<LoadCalculationResponse> calculate(@RequestBody LoadCalculationRequest request) {
        LoadCalculationResponse response = loadCalculationService.calculatePrice(request);
        return ResponseEntity.ok(response);
    }
}
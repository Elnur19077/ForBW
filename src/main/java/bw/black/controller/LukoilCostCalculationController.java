package bw.black.controller;

import bw.black.dto.request.LukoilCostCalculationRequest;
import bw.black.dto.response.LukoilCostCalculationResponse;
import bw.black.entity.LukoilCostCalculation;
import bw.black.service.LukoilCostCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lukoil-costs")
@RequiredArgsConstructor
public class LukoilCostCalculationController {
    private final LukoilCostCalculationService lukoilCostCalculationService;

    @PostMapping("/calculate")
    public LukoilCostCalculationResponse calculate(@RequestBody LukoilCostCalculationRequest request) {
        return lukoilCostCalculationService.calculate(request);
    }

    @PostMapping("/submit")
    public LukoilCostCalculationResponse submit(@RequestBody LukoilCostCalculationRequest request) {
        return lukoilCostCalculationService.submit(request);
    }

    @GetMapping
    public List<LukoilCostCalculation> getAll() {
        return lukoilCostCalculationService.getAllCalculations();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lukoilCostCalculationService.deleteCalculation(id);
    }
}

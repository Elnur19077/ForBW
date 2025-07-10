package bw.black.controller;

import bw.black.dto.request.LukoilCostCalculationRequest;
import bw.black.dto.response.CostCalculationResponse;
import bw.black.dto.response.LukoilCostCalculationResponse;
import bw.black.entity.LukoilCostCalculation;
import bw.black.service.LukoilCostCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lukoil-costs")
@RequiredArgsConstructor
public class LukoilCostCalculationController {
    private final LukoilCostCalculationService lukoilCostCalculationService;
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @PostMapping("/calculate")
    public LukoilCostCalculationResponse calculate(@RequestBody LukoilCostCalculationRequest request) {
        return lukoilCostCalculationService.calculate(request);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping("/submit")
    public LukoilCostCalculationResponse submit(@RequestBody LukoilCostCalculationRequest request) {
        return lukoilCostCalculationService.submit(request);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @GetMapping
    public List<LukoilCostCalculation> getAll() {
        return lukoilCostCalculationService.getAllCalculations();
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lukoilCostCalculationService.deleteCalculation(id);
    }

}

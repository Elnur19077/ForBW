package bw.black.controller;

import bw.black.dto.request.UAECostCalculationRequest;
import bw.black.dto.response.UAECostCalculationResponse;
import bw.black.entity.UAEProduct;
import bw.black.service.UAECostCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uae-cost")
@RequiredArgsConstructor
public class UAECostCalculationController {
   private final UAECostCalculationService uaeCostCalculationService;

    @PostMapping("/calculate")
    public UAECostCalculationResponse calculate(@RequestBody UAECostCalculationRequest request) {
        return uaeCostCalculationService.calculate(request);
    }

    @PostMapping("/submit")
    public UAECostCalculationResponse submit(@RequestBody UAECostCalculationRequest request) {
        return uaeCostCalculationService.submit(request);
    }

    @GetMapping("/all")
    public List<UAEProduct> getAll() {
        return uaeCostCalculationService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        uaeCostCalculationService.delete(id);
    }

    @GetMapping("/search")
    public List<UAEProduct> search(@RequestParam String keyword) {
        return uaeCostCalculationService.search(keyword);
    }
}

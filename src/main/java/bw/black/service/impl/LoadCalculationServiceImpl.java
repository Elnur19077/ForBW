package bw.black.service.impl;

import bw.black.dto.request.LoadCalculationRequest;
import bw.black.dto.response.LoadCalculationResponse;
import bw.black.entity.Zone;
import bw.black.entity.ZonePricing;
import bw.black.repository.ZonePricingRepository;
import bw.black.repository.ZoneRepository;
import bw.black.service.LoadCalculationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LoadCalculationServiceImpl implements LoadCalculationService {
    private final ZoneRepository zoneRepository;
    private final ZonePricingRepository pricingRepository;

    @Override
    public LoadCalculationResponse calculatePrice(LoadCalculationRequest request) {
        Zone zone = zoneRepository.findByCountry(request.getCountry())
                .orElseThrow(() -> new RuntimeException("Country not found in any zone"));

        ZonePricing pricing = pricingRepository.findByZoneAndWeight(zone, request.getWeight())
                .orElseThrow(() -> new RuntimeException("Pricing not found for this weight in the zone"));

        return new LoadCalculationResponse(
                zone.getName(),
                request.getCountry(),
                request.getWeight(),
                pricing.getPrice()
        );
    }

}

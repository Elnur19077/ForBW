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

import java.util.List;

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

        List<ZonePricing> pricingList = pricingRepository.findAllByZoneAndWeight(zone, request.getWeight());

        if (pricingList.isEmpty()) {
            throw new RuntimeException("Pricing not found for this weight in the zone");
        }

        // Əgər bir neçə varsa, sadəcə birini seç (məsələn, ilkini)
        ZonePricing pricing = pricingList.get(0); // və ya daha optimalı seçmək üçün filterləmək olar

        return new LoadCalculationResponse(
                zone.getName(),
                request.getCountry(),
                request.getWeight(),
                pricing.getPrice()
        );
    }

}

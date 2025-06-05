package bw.black.service.impl;

import bw.black.dto.request.CostCalculationRequest;
import bw.black.dto.response.CostCalculationResponse;
import bw.black.entity.CostCalculation;
import bw.black.repository.CostCalculationRepository;
import bw.black.service.CostCalculationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CostCalculationServiceImpl implements CostCalculationService {
    private final CostCalculationRepository repository;


    @Override
    public CostCalculationResponse calculate(CostCalculationRequest request) {
        double basePrice = request.getBasePrice();
        double transportCost = request.getTransportCost();
        double customsRate = request.getCustomsDuty();

        // Default dəyərlər
        double brokerCost = request.getBrokerCost() != null ? request.getBrokerCost() : 200;
        double antiMonopolyFee = request.getAntiMonopolyFee() != null ? request.getAntiMonopolyFee() : 100;
        double profitPercentage = request.getProfitPercentage() != null ? request.getProfitPercentage() : 25;

        // Gömrük haqqı
        double customsFee = (basePrice + transportCost) * customsRate / 100;

        // Toplam maya dəyəri
        double totalCost = basePrice + transportCost + customsFee + brokerCost + antiMonopolyFee;

        // Satış qiyməti (mənfəət faizi ilə)
        double finalPrice = totalCost * (1 + profitPercentage / 100);

        // Gross Profit
        double grossProfit = finalPrice - totalCost;

        // Net Profit (vergi və rüsumlar çıxdıqdan sonra)
        double netProfit = grossProfit * 0.80 * 0.95 * 0.98;

        // Ofis xərclərinin qarşılanması
        double yearlyOfficeCost = 120000;
        double monthlyOfficeCost = 10000;
        double yearlyCoverage = (netProfit / yearlyOfficeCost) * 100;
        double monthlyCoverage = (netProfit / monthlyOfficeCost) * 100;

        // Entity DB-yə yazılır
        CostCalculation entity = CostCalculation.builder()
                .basePrice(basePrice)
                .transportCost(transportCost)
                .customsDuty(customsRate)
                .brokerCost(brokerCost)
                .antiMonopolyFee(antiMonopolyFee)
                .profitPercentage(profitPercentage)
                .finalPrice(finalPrice)
                .netProfit(netProfit)
                .build();

        repository.save(entity);

        return CostCalculationResponse.builder()
                .finalPrice(finalPrice)
                .netProfit(netProfit)
                .yearlyCoveragePercentage(yearlyCoverage)
                .monthlyCoveragePercentage(monthlyCoverage)
                .build();
    }
}


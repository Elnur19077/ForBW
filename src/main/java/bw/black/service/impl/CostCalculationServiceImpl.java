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
        int unitCount = request.getUnitCount();
        double transportCost = request.getTransportCost();
        double customsRate = request.getCustomsDuty();
        double brokerCost = request.getBrokerCost() != null ? request.getBrokerCost() : 200;
        double antiMonopolyFee = request.getAntiMonopolyFee() != null ? request.getAntiMonopolyFee() : 100;
        double profitPercentage = request.getProfitPercentage() != null ? request.getProfitPercentage() : 25;
        double employeeBonusPercent = request.getEmployeeBonusPercent() != null ? request.getEmployeeBonusPercent() : 0;
        double customerBonusPercent = request.getCustomerBonusPercent() != null ? request.getCustomerBonusPercent() : 0;
        int guaranteeMonths = request.getGuaranteeMonths() != null ? request.getGuaranteeMonths() : 0;

        double unitPrice = basePrice * unitCount;

        double customsFee = (unitPrice + transportCost) * customsRate / 100;

        double totalCost = unitPrice + transportCost + customsFee + brokerCost + antiMonopolyFee;

        double finalPrice = totalCost * (1 + profitPercentage / 100);

        double vatAmount = finalPrice * 0.18;
        double bankGuarantee = finalPrice * (0.0025 * guaranteeMonths);

        double grossProfit = finalPrice - totalCost;

        double netProfit = grossProfit * 0.80 * 0.95 * 0.98;

        double employeeBonus = netProfit * employeeBonusPercent / 100;
        double customerBonus = netProfit * customerBonusPercent / 100;

        double netProfitAfterBonuses = netProfit - employeeBonus - customerBonus;

        // Coverage calculations
        double yearlyOfficeCost = 120_000;
        double monthlyOfficeCost = 10_000;
        double turnover = 1_500_000;

        double yearlyCoverage = (netProfitAfterBonuses / yearlyOfficeCost) * 100;
        double monthlyCoverage = (netProfitAfterBonuses / monthlyOfficeCost) * 100;
        double turnoverCoverage = (finalPrice / turnover) * 100;

        // Save to DB
        CostCalculation entity = CostCalculation.builder()
                .basePrice(basePrice)
                .unitCount(unitCount)
                .hsCode(request.getHsCode())
                .transportCost(transportCost)
                .customsDuty(customsRate)
                .brokerCost(brokerCost)
                .antiMonopolyFee(antiMonopolyFee)
                .profitPercentage(profitPercentage)
                .employeeBonusPercent(employeeBonusPercent)
                .customerBonusPercent(customerBonusPercent)
                .guaranteeMonths(guaranteeMonths)
                .unitPrice(unitPrice)
                .customsFee(customsFee)
                .totalCost(totalCost)
                .finalPrice(finalPrice)
                .vatAmount(vatAmount)
                .bankGuarantee(bankGuarantee)
                .grossProfit(grossProfit)
                .netProfit(netProfitAfterBonuses)
                .employeeBonus(employeeBonus)
                .customerBonus(customerBonus)
                .yearlyCoveragePercentage(yearlyCoverage)
                .monthlyCoveragePercentage(monthlyCoverage)
                .turnoverCoveragePercentage(turnoverCoverage)
                .build();

        repository.save(entity);

        return CostCalculationResponse.builder()
                .unitPrice(unitPrice)
                .finalPrice(finalPrice)
                .netProfit(netProfitAfterBonuses)
                .employeeBonus(employeeBonus)
                .customerBonus(customerBonus)
                .yearlyCoveragePercentage(yearlyCoverage)
                .monthlyCoveragePercentage(monthlyCoverage)
                .turnoverCoveragePercentage(turnoverCoverage)
                .build();
    }
}


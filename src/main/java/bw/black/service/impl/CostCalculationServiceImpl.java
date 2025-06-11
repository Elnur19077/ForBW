package bw.black.service.impl;

import bw.black.dto.request.CostCalculationRequest;
import bw.black.dto.request.ProductRequest;
import bw.black.dto.response.CostCalculationResponse;
import bw.black.dto.response.SingleProductResponse;
import bw.black.entity.CostCalculation;
import bw.black.repository.CostCalculationRepository;
import bw.black.service.CostCalculationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CostCalculationServiceImpl implements CostCalculationService {
    private final CostCalculationRepository repository;


    @Override
    public CostCalculationResponse calculate(CostCalculationRequest request) {
        List<SingleProductResponse> productResponses = new ArrayList<>();

        double totalFinalPrice = 0;
        double totalNetProfit = 0;
        double totalEmployeeBonus = 0;
        double totalCustomerBonus = 0;
        double totalBankGuarantee = 0;

        for (ProductRequest product : request.getProducts()) {
            double basePrice = product.getBasePrice();
            int unitCount = product.getUnitCount();
            double unitPrice = basePrice * unitCount;
            double hsCodeDuty = product.getHsCodeDuty() != null ? product.getHsCodeDuty() : request.getCustomsDuty();
            double customsFee = (unitPrice + request.getTransportCost()) * hsCodeDuty / 100;

            double totalCost = unitPrice + request.getTransportCost()
                    + customsFee
                    + (request.getBrokerCost() != null ? request.getBrokerCost() : 200)
                    + (request.getAntiMonopolyFee() != null ? request.getAntiMonopolyFee() : 100);

            double profitPercentage = request.getProfitPercentage() != null ? request.getProfitPercentage() : 25;
            double finalPrice = totalCost * (1 + profitPercentage / 100);

            double vatAmount = finalPrice * 0.18;
            double guaranteeMonths = request.getGuaranteeMonths() != null ? request.getGuaranteeMonths() : 0;
            double bankGuarantee = finalPrice * (0.0025 * guaranteeMonths);

            double grossProfit = finalPrice - totalCost;
            double netProfit = grossProfit * 0.80 * 0.95 * 0.98;

            double employeeBonus = netProfit * (request.getEmployeeBonusPercent() != null ? request.getEmployeeBonusPercent() : 0) / 100;
            double customerBonus = netProfit * (request.getCustomerBonusPercent() != null ? request.getCustomerBonusPercent() : 0) / 100;

            double netProfitAfterBonuses = netProfit - employeeBonus - customerBonus;

            // Save individual product to DB (optional per product)
            repository.save(
                    CostCalculation.builder()
                            .basePrice(basePrice)
                            .unitCount(unitCount)
                            .hsCode(product.getHsCode())
                            .transportCost(request.getTransportCost())
                            .customsDuty(hsCodeDuty)
                            .brokerCost(request.getBrokerCost())
                            .antiMonopolyFee(request.getAntiMonopolyFee())
                            .profitPercentage(profitPercentage)
                            .employeeBonusPercent(request.getEmployeeBonusPercent())
                            .customerBonusPercent(request.getCustomerBonusPercent())
                            .guaranteeMonths(request.getGuaranteeMonths())
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
                            .build()
            );

            totalFinalPrice += finalPrice;
            totalNetProfit += netProfitAfterBonuses;
            totalEmployeeBonus += employeeBonus;
            totalCustomerBonus += customerBonus;
            totalBankGuarantee += bankGuarantee;

            productResponses.add(SingleProductResponse.builder()
                    .hsCode(product.getHsCode())
                    .unitPrice(unitPrice)
                    .customsFee(customsFee)
                    .totalCost(totalCost)
                    .finalPrice(finalPrice)
                    .vatAmount(vatAmount)
                    .grossProfit(grossProfit)
                    .netProfit(netProfitAfterBonuses)
                    .build());
        }

        double yearlyOfficeCost = 120_000;
        double monthlyOfficeCost = 10_000;
        double turnover = 1_500_000;

        return CostCalculationResponse.builder()
                .products(productResponses)
                .totalFinalPrice(totalFinalPrice)
                .totalNetProfit(totalNetProfit)
                .totalEmployeeBonus(totalEmployeeBonus)
                .totalCustomerBonus(totalCustomerBonus)
                .totalBankGuaranteeAmount(totalBankGuarantee)
                .yearlyCoveragePercentage((totalNetProfit / yearlyOfficeCost) * 100)
                .monthlyCoveragePercentage((totalNetProfit / monthlyOfficeCost) * 100)
                .turnoverCoveragePercentage((totalFinalPrice / turnover) * 100)
                .build();
    }
}


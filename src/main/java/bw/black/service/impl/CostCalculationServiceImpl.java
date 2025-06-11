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

        // Transport ümumi olaraq bir dəfə əlavə olunur, məhsulların üzərinə bölünmür
        double totalTransportCost = request.getTransportCost() != null ? request.getTransportCost() : 0;

        // Broker və antiMonopolyFee məhsul başına ayrıca gəlir
        double brokerCost = request.getBrokerCost() != null ? request.getBrokerCost() : 200;

        for (ProductRequest product : request.getProducts()) {
            double basePrice = product.getBasePrice();
            int unitCount = product.getUnitCount();
            double unitPrice = basePrice * unitCount;

            // Customs duty individual (product səviyyəsində varsa) və ya ümumi
            double hsCodeDuty = product.getHsCodeDuty() != null ? product.getHsCodeDuty() : request.getCustomsDuty();

            // Customs fee hesabı: unitPrice + transport (ümumi) üzərində faiz
            double customsFee = (unitPrice + totalTransportCost) * hsCodeDuty / 100;

            // AntiMonopolyFee hər məhsul üçün ayrıca, ya məhsuldan, ya da requestdən, default 100
            double antiMonopolyFee = product.getAntiMonopolyFee() != null ? product.getAntiMonopolyFee() : (request.getAntiMonopolyFee() != null ? request.getAntiMonopolyFee() : 100);

            // Burada diqqət: transport ümumi olaraq əlavə olunur, məhsulun üzərinə ayrıca yox
            double totalCost = unitPrice + totalTransportCost + customsFee + brokerCost + antiMonopolyFee;

            double profitPercentage = request.getProfitPercentage() != null ? request.getProfitPercentage() : 25;
            double finalPrice = totalCost * (1 + profitPercentage / 100);

            double vatAmount = finalPrice * 0.18;
            int guaranteeMonths = request.getGuaranteeMonths() != null ? request.getGuaranteeMonths() : 0;
            double bankGuarantee = finalPrice * (0.0025 * guaranteeMonths);

            double grossProfit = finalPrice - totalCost;
            double netProfit = grossProfit * 0.80 * 0.95 * 0.98;

            double employeeBonusPercent = request.getEmployeeBonusPercent() != null ? request.getEmployeeBonusPercent() : 0;
            double customerBonusPercent = request.getCustomerBonusPercent() != null ? request.getCustomerBonusPercent() : 0;

            double employeeBonus = netProfit * employeeBonusPercent / 100;
            double customerBonus = netProfit * customerBonusPercent / 100;

            double netProfitAfterBonuses = netProfit - employeeBonus - customerBonus;

            // DB-yə qeyd (istəyə bağlı)
            repository.save(
                    CostCalculation.builder()
                            .basePrice(basePrice)
                            .unitCount(unitCount)
                            .hsCode(product.getHsCode())
                            .transportCost(totalTransportCost) // ümumi transport burada qeyd olunur
                            .customsDuty(hsCodeDuty)
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


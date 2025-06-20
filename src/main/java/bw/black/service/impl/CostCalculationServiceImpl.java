package bw.black.service.impl;

import bw.black.dto.request.CostCalculationRequest;
import bw.black.dto.request.ProductRequest;
import bw.black.dto.response.CostCalculationResponse;
import bw.black.dto.response.SingleProductResponse;

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

        if (request.getProducts() == null || request.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Products list cannot be null or empty.");
        }

        List<SingleProductResponse> productResponses = new ArrayList<>();

        double totalTransportCost = request.getTransportCost() != null ? request.getTransportCost() : 0;
        double brokerCost = request.getBrokerCost() != null ? request.getBrokerCost() : 200;
        double profitPercentage = request.getProfitPercentage() != null ? request.getProfitPercentage() : 30;
        int guaranteeMonths = request.getGuaranteeMonths() != null ? request.getGuaranteeMonths() : 0;
        double employeeBonusPercent = request.getEmployeeBonusPercent() != null ? request.getEmployeeBonusPercent() : 0;
        double customerBonusPercent = request.getCustomerBonusPercent() != null ? request.getCustomerBonusPercent() : 0;

        int productCount = request.getProducts().size();
        double sharedTransportPerProduct = totalTransportCost / productCount;
        double sharedBrokerPerProduct = brokerCost / productCount;

        double totalFinalPrice = 0;
        double totalNetProfit = 0;
        double totalEmployeeBonus = 0;
        double totalCustomerBonus = 0;
        double totalGrossProfit = 0;
        double totalVatAmount = 0;

        double extraBeforeProfit = 45;

        for (ProductRequest product : request.getProducts()) {
            double basePrice = product.getBasePrice();
            int unitCount = product.getUnitCount();
            double unitPrice = basePrice * unitCount;

            double hsCodeDuty = product.getHsCodeDuty() != null
                    ? product.getHsCodeDuty()
                    : request.getCustomsDuty();

            double customsBase = unitPrice + sharedTransportPerProduct;
            double customsFee = customsBase * hsCodeDuty / 100;

            double antiMonopolyFee = product.getAntiMonopolyFee() != null
                    ? product.getAntiMonopolyFee()
                    : (request.getAntiMonopolyFee() != null ? request.getAntiMonopolyFee() : 150);

            double totalCost = unitPrice + sharedTransportPerProduct + customsFee + antiMonopolyFee + sharedBrokerPerProduct;

            double costWithExtra = totalCost + extraBeforeProfit;

            double finalPrice = costWithExtra * (1 + profitPercentage / 100);

            double vatAmount = finalPrice * 0.18;
            totalVatAmount += vatAmount;

            double grossProfit = finalPrice - costWithExtra;
            totalGrossProfit += grossProfit;

            // Ofis xərci = costWithExtra-nın 10%-i
            double officeCost = costWithExtra * 0.10;
            double profitAfterOffice = grossProfit - officeCost;

            // Bank zəmanəti bu məhsul üçün (ƏDV də daxil olmaqla)
            double bankGuarantee = (finalPrice + vatAmount) * 0.0025 * guaranteeMonths;
            double profitAfterBankGuarantee = profitAfterOffice - bankGuarantee;

            // Mərhələli çıxılmalar
            double after20 = profitAfterBankGuarantee * 0.80;
            double after5 = after20 * 0.95;
            double netProfitBeforeBonuses = after5 * 0.98;

            // Bonuslar çıxılır
            double employeeBonus = netProfitBeforeBonuses * employeeBonusPercent / 100;
            double customerBonus = netProfitBeforeBonuses * customerBonusPercent / 100;
            double netProfit = netProfitBeforeBonuses - employeeBonus - customerBonus;

            totalFinalPrice += finalPrice;
            totalNetProfit += netProfit;
            totalEmployeeBonus += employeeBonus;
            totalCustomerBonus += customerBonus;

            productResponses.add(SingleProductResponse.builder()
                    .hsCode(product.getHsCode())
                    .unitPrice(unitPrice)
                    .customsFee(customsFee)
                    .totalCost(totalCost)
                    .finalPrice(finalPrice)
                    .vatAmount(vatAmount)
                    .grossProfit(grossProfit)
                    .netProfit(netProfit)
                    .build());
        }

        // Toplam bank zəmanəti: totalFinalPrice + ƏDV əsasında hesablanır
        double totalBankGuarantee = (totalFinalPrice + totalVatAmount) * 0.0025 * guaranteeMonths;

        double yearlyOfficeCost = 120_000;
        double monthlyOfficeCost = 10_000;
        double turnover = 1_500_000;

        double officeCoverageAmount = totalGrossProfit * 0.10;

        return CostCalculationResponse.builder()
                .products(productResponses)
                .totalFinalPrice(totalFinalPrice)
                .totalNetProfit(totalNetProfit)
                .totalEmployeeBonus(totalEmployeeBonus)
                .totalCustomerBonus(totalCustomerBonus)
                .totalBankGuaranteeAmount(totalBankGuarantee)
                .yearlyCoveragePercentage((officeCoverageAmount / yearlyOfficeCost) * 100)
                .monthlyCoveragePercentage((officeCoverageAmount / monthlyOfficeCost) * 100)
                .grossProfitToTurnoverPercentage((totalGrossProfit / turnover) * 100)
                .netToGrossProfitEfficiency((totalNetProfit / totalGrossProfit) * 100)
                .build();

    }


}

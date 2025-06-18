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
        double profitPercentage = request.getProfitPercentage() != null ? request.getProfitPercentage() : 25;
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
                    : (request.getAntiMonopolyFee() != null ? request.getAntiMonopolyFee() : 100);

            // Broker də bu mərhələdə əlavə olunur
            double totalCost = unitPrice + sharedTransportPerProduct + customsFee + antiMonopolyFee + sharedBrokerPerProduct;

            double finalPrice = totalCost * (1 + profitPercentage / 100);
            double vatAmount = finalPrice * 0.18;

            double grossProfit = finalPrice - totalCost;
            double netProfit = grossProfit * 0.80 * 0.95 * 0.98;

            double employeeBonus = netProfit * employeeBonusPercent / 100;
            double customerBonus = netProfit * customerBonusPercent / 100;
            double netProfitAfterBonuses = netProfit - employeeBonus - customerBonus;

            totalFinalPrice += finalPrice;
            totalNetProfit += netProfitAfterBonuses;
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
                    .netProfit(netProfitAfterBonuses)
                    .build());
        }

        double totalBankGuarantee = totalFinalPrice * 0.0025 * guaranteeMonths;

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




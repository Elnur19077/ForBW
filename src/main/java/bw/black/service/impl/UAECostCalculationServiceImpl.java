package bw.black.service.impl;

import bw.black.dto.request.UAECostCalculationRequest;
import bw.black.dto.request.UAEProductRequest;
import bw.black.dto.response.UAECostCalculationResponse;
import bw.black.dto.response.UAEProductResponse;
import bw.black.entity.UAEProduct;
import bw.black.repository.UAEProductRepository;

import bw.black.service.UAECostCalculationService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Primary
@RequiredArgsConstructor
public class UAECostCalculationServiceImpl  implements UAECostCalculationService {

    private final UAEProductRepository repository;

    private static final double MONTHLY_EXPENSE = 12000;
    private static final double ANNUAL_TURNOVER = 800000;

    @Override
    public UAECostCalculationResponse calculate(UAECostCalculationRequest request) {
        List<UAEProductResponse> responses = new ArrayList<>();
        double totalNet = 0;
        double totalGross = 0;
        double totalFinalSales = 0;

        for (UAEProductRequest p : request.getProducts()) {
            UAEProductResponse resp = calculateSingle(p);
            responses.add(resp);
            totalNet += resp.getProfitAfterBonuses();
            totalGross += resp.getGrossProfit();
            totalFinalSales += resp.getFinalSalesPrice();
        }

        double monthlyCoverage = (totalNet / MONTHLY_EXPENSE) * 100;
        double annualCoverage = (totalNet / ANNUAL_TURNOVER) * 100;

        return UAECostCalculationResponse.builder()
                .rfqNo(request.getRfqNo())
                .clientName(request.getClientName())
                .products(responses)
                .totalNetProfit(totalNet)
                .totalGrossProfit(totalGross)
                .totalFinalSales(totalFinalSales)
                .totalMonthlyCoverage(monthlyCoverage)
                .totalAnnualCoverage(annualCoverage)
                .build();
    }

    @Override
    public UAECostCalculationResponse submit(UAECostCalculationRequest request) {
        List<UAEProductResponse> responses = new ArrayList<>();
        double totalNet = 0;
        double totalGross = 0;
        double totalFinalSales = 0;

        for (UAEProductRequest p : request.getProducts()) {
            UAEProductResponse resp = calculateSingle(p);
            totalNet += resp.getProfitAfterBonuses();
            totalGross += resp.getGrossProfit();
            totalFinalSales += resp.getFinalSalesPrice();

            repository.save(UAEProduct.builder()
                    .rfqNo(request.getRfqNo())
                    .clientName(request.getClientName())
                    .productName(p.getProductName())
                    .unitCount(p.getUnitCount())
                    .goodsPrice(p.getGoodsPrice())
                    .transportCost(p.getTransportCost())
                    .otherCost(p.getOtherCost())
                    .warehouseCost(p.getWarehouseCost())
                    .marginPercent(p.getMarginPercent())
                    .employeeBonus(p.getEmployeeBonus())
                    .customerBonus(p.getCustomerBonus())
                    .totalCostWithoutMargin(resp.getTotalCostWithoutMargin())
                    .finalSalesPrice(resp.getFinalSalesPrice())
                    .grossProfit(resp.getGrossProfit())
                    .netProfit(resp.getNetProfit())
                    .profitAfterBonuses(resp.getProfitAfterBonuses())
                    .monthlyCoverage(resp.getMonthlyCoverage())
                    .annualCoverage(resp.getAnnualCoverage())
                    .createdAt(LocalDateTime.now())
                    .build());

            responses.add(resp);
        }

        double monthlyCoverage = (totalNet / MONTHLY_EXPENSE) * 100;
        double annualCoverage = (totalNet / ANNUAL_TURNOVER) * 100;

        return UAECostCalculationResponse.builder()
                .rfqNo(request.getRfqNo())
                .clientName(request.getClientName())
                .products(responses)
                .totalNetProfit(totalNet)
                .totalGrossProfit(totalGross)
                .totalFinalSales(totalFinalSales)
                .totalMonthlyCoverage(monthlyCoverage)
                .totalAnnualCoverage(annualCoverage)
                .build();
    }

    private UAEProductResponse calculateSingle(UAEProductRequest p) {
        double goodsTotal = p.getGoodsPrice() * p.getUnitCount();
        double costWithoutMargin = goodsTotal + p.getTransportCost() + p.getOtherCost() + p.getWarehouseCost();
        double finalSalesPrice = costWithoutMargin * (1 + p.getMarginPercent() / 100);
        double gross = finalSalesPrice - costWithoutMargin;
        double net = gross - (finalSalesPrice * 0.10) - (finalSalesPrice * 0.02);
        double afterBonuses = net - p.getEmployeeBonus() - p.getCustomerBonus();
        double monthlyCoverage = (afterBonuses / MONTHLY_EXPENSE) * 100;
        double annualCoverage = (afterBonuses / ANNUAL_TURNOVER) * 100;

        return UAEProductResponse.builder()
                .productName(p.getProductName())
                .totalCostWithoutMargin(costWithoutMargin)
                .finalSalesPrice(finalSalesPrice)
                .grossProfit(gross)
                .netProfit(net)
                .profitAfterBonuses(afterBonuses)
                .monthlyCoverage(monthlyCoverage)
                .annualCoverage(annualCoverage)
                .build();
    }

    @Override
    public List<UAEProduct> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UAEProduct> search(String keyword) {
        return repository.findByProductNameContainingIgnoreCase(keyword);
    }
}

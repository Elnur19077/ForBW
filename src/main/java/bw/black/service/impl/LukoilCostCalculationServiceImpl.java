package bw.black.service.impl;

import bw.black.dto.request.LukoilCostCalculationRequest;
import bw.black.dto.request.LukoilProductRequest;
import bw.black.dto.response.LukoilCostCalculationResponse;
import bw.black.dto.response.LukoilProductResponse;
import bw.black.entity.LukoilCostCalculation;
import bw.black.repository.LukoilCostCalculationRepository;
import bw.black.service.LukoilCostCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LukoilCostCalculationServiceImpl implements LukoilCostCalculationService {
    private final LukoilCostCalculationRepository repository;

    @Override
    public LukoilCostCalculationResponse calculate(LukoilCostCalculationRequest request) {
        return buildResponse(request);
    }

    @Override
    public LukoilCostCalculationResponse submit(LukoilCostCalculationRequest request) {
        List<LukoilProductResponse> productResponses = new ArrayList<>();
        double totalProfit = 0;

        for (LukoilProductRequest p : request.getProducts()) {
            LukoilProductResponse resp = calculateSingle(p);
            totalProfit += resp.getNetProfit();

            repository.save(LukoilCostCalculation.builder()
                    .rfqNo(request.getRfqNo())
                    .clientName(request.getClientName())
                    .productName(p.getProductName())
                    .goods(p.getGoods())
                    .unit(p.getUnit())
                    .transportToFCA(p.getTransportToFCA())
                    .transportToDDP(p.getTransportToDDP())
                    .otherCost(p.getOtherCost())
                    .totalCostWithoutMargin(resp.getTotalCostWithoutMargin())
                    .internalSalesCost(resp.getInternalSalesCost())
                    .grossMargin(resp.getGrossMargin())
                    .firstNetMargin(resp.getFirstNetMargin())
                    .secondNetMargin(resp.getSecondNetMargin())
                    .finalNetMargin(resp.getFinalNetMargin())
                    .lukoilSalesPrice(resp.getLukoilSalesPrice())
                    .netProfit(resp.getNetProfit())
                    .createdAt(LocalDateTime.now())
                    .build());

            productResponses.add(resp);
        }

        return LukoilCostCalculationResponse.builder()
                .rfqNo(request.getRfqNo())
                .clientName(request.getClientName())
                .products(productResponses)
                .totalNetProfit(totalProfit)
                .build();
    }

    private LukoilProductResponse calculateSingle(LukoilProductRequest p) {
        double baseGoods = p.getGoods() * p.getUnit();
        double totalCost = baseGoods + p.getTransportToFCA() + p.getTransportToDDP() + p.getOtherCost();
        double internalSalesCost = totalCost * (1 + p.getMarginPercent() / 100);
        double gross = internalSalesCost - totalCost;
        double firstMargin = gross - (internalSalesCost * 0.10);
        double secondMargin = firstMargin * 0.91;
        double finalMargin = secondMargin * 0.98;
        double lukoilSalesPrice = internalSalesCost * 1.08;
        double netProfit = finalMargin;

        return LukoilProductResponse.builder()
                .productName(p.getProductName())
                .totalCostWithoutMargin(totalCost)
                .internalSalesCost(internalSalesCost)
                .grossMargin(gross)
                .firstNetMargin(firstMargin)
                .secondNetMargin(secondMargin)
                .finalNetMargin(finalMargin)
                .lukoilSalesPrice(lukoilSalesPrice)
                .netProfit(netProfit)
                .build();
    }

    private LukoilCostCalculationResponse buildResponse(LukoilCostCalculationRequest request) {
        List<LukoilProductResponse> productResponses = new ArrayList<>();
        double totalProfit = 0;

        for (LukoilProductRequest p : request.getProducts()) {
            LukoilProductResponse resp = calculateSingle(p);
            productResponses.add(resp);
            totalProfit += resp.getNetProfit();
        }

        return LukoilCostCalculationResponse.builder()
                .rfqNo(request.getRfqNo())
                .clientName(request.getClientName())
                .products(productResponses)
                .totalNetProfit(totalProfit)
                .build();
    }

    @Override
    public List<LukoilCostCalculation> getAllCalculations() {
        return repository.findAll();
    }

    @Override
    public void deleteCalculation(Long id) {
        repository.deleteById(id);
    }
    @Override
    public List<LukoilCostCalculation> searchByProductName(String keyword) {
        return repository.findByProductNameContainingIgnoreCase(keyword);
    }

}


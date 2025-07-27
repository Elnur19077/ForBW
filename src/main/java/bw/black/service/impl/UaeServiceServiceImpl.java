package bw.black.service.impl;

import bw.black.dto.request.UaeServiceProductRequest;
import bw.black.dto.request.UaeServiceRequest;
import bw.black.dto.response.UaeServiceProductResponse;
import bw.black.dto.response.UaeServiceResponse;
import bw.black.entity.UaeService;
import bw.black.repository.UaeServiceRepository;
import bw.black.service.UaeServiceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Primary
@RequiredArgsConstructor
public class UaeServiceServiceImpl implements UaeServiceService {

    private final UaeServiceRepository repository;
    private static final double ANNUAL_TURNOVER = 800_000;
    private static final double MONTHLY_OFFICE_COST = 12_000;

    @Override
    public UaeServiceResponse calculate(UaeServiceRequest request) {
        return process(request, false);
    }

    @Override
    public UaeServiceResponse submit(UaeServiceRequest request) {
        return process(request, true);
    }

    private UaeServiceResponse process(UaeServiceRequest request, boolean saveToDb) {
        List<UaeServiceProductResponse> productResponses = new ArrayList<>();
        double totalNet = 0, totalGross = 0, totalSales = 0, totalMonthlyCov = 0, totalAnnualCov = 0;

        for (UaeServiceProductRequest p : request.getProducts()) {
            double totalCost = p.getGoodsPrice() + p.getTransportCost() + p.getOtherCost() + p.getWarehouseCost();
            double finalPrice = totalCost * (1 + p.getMarginPercent() / 100);
            double gross = finalPrice - totalCost;

            double cost10 = finalPrice * 0.10;
            double grossAfter10 = gross - cost10;
            double grossAfter9 = grossAfter10 - (grossAfter10 * 0.09);
            double net = grossAfter9 - (grossAfter9 * 0.02);

            double afterBonuses = net - p.getEmployeeBonus() - p.getCustomerBonus();
            double monthlyCov = (afterBonuses / MONTHLY_OFFICE_COST) * 100;
            double annualCov = (afterBonuses / ANNUAL_TURNOVER) * 100;

            totalNet += afterBonuses;
            totalGross += gross;
            totalSales += finalPrice;
            totalMonthlyCov += monthlyCov;
            totalAnnualCov += annualCov;

            if (saveToDb) {
                UaeService entity = UaeService.builder()
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
                        .totalCostWithoutMargin(totalCost)
                        .finalSalesPrice(finalPrice)
                        .grossProfit(gross)
                        .netProfit(net)
                        .profitAfterBonuses(afterBonuses)
                        .monthlyCoverage(monthlyCov)
                        .annualCoverage(annualCov)
                        .build();
                repository.save(entity);
            }

            productResponses.add(UaeServiceProductResponse.builder()
                    .productName(p.getProductName())
                    .totalCostWithoutMargin(totalCost)
                    .finalSalesPrice(finalPrice)
                    .grossProfit(gross)
                    .netProfit(net)
                    .profitAfterBonuses(afterBonuses)
                    .monthlyCoverage(monthlyCov)
                    .annualCoverage(annualCov)
                    .build());
        }

        return UaeServiceResponse.builder()
                .rfqNo(request.getRfqNo())
                .clientName(request.getClientName())
                .products(productResponses)
                .totalNetProfit(totalNet)
                .totalGrossProfit(totalGross)
                .totalFinalSales(totalSales)
                .totalMonthlyCoverage(totalMonthlyCov)
                .totalAnnualCoverage(totalAnnualCov)
                .build();
    }

    @Override
    public List<UaeServiceResponse> findAll() {
        List<UaeService> all = repository.findAll();
        return groupByRFQ(all);
    }

    @Override
    public UaeServiceResponse findById(Long id) {
        UaeService entry = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return groupByRFQ(List.of(entry)).get(0);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UaeServiceResponse update(Long id, UaeServiceRequest request) {
        repository.deleteById(id);
        return submit(request);
    }

    private List<UaeServiceResponse> groupByRFQ(List<UaeService> entries) {
        List<UaeServiceResponse> responses = new ArrayList<>();

        for (UaeService e : entries) {
            List<UaeServiceProductResponse> productResponses = new ArrayList<>();

            productResponses.add(UaeServiceProductResponse.builder()
                    .productName(e.getProductName())
                    .totalCostWithoutMargin(e.getTotalCostWithoutMargin())
                    .finalSalesPrice(e.getFinalSalesPrice())
                    .grossProfit(e.getGrossProfit())
                    .netProfit(e.getNetProfit())
                    .profitAfterBonuses(e.getProfitAfterBonuses())
                    .monthlyCoverage(e.getMonthlyCoverage())
                    .annualCoverage(e.getAnnualCoverage())
                    .build());

            responses.add(UaeServiceResponse.builder()
                    .rfqNo(Optional.ofNullable(e.getRfqNo()).orElse("UNKNOWN"))
                    .clientName(Optional.ofNullable(e.getClientName()).orElse("UNKNOWN_CLIENT"))
                    .products(productResponses) // 1 məhsul = 1 cavab
                    .totalNetProfit(e.getProfitAfterBonuses())
                    .totalGrossProfit(e.getGrossProfit())
                    .totalFinalSales(e.getFinalSalesPrice())
                    .totalMonthlyCoverage(e.getMonthlyCoverage())
                    .totalAnnualCoverage(e.getAnnualCoverage())
                    .build());
        }

        return responses;
    }


}

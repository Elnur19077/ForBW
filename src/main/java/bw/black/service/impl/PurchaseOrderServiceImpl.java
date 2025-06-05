package bw.black.service.impl;

import bw.black.dto.request.PurchaseOrderRequest;
import bw.black.dto.response.PurchaseOrderResponse;
import bw.black.entity.PurchaseOrder;
import bw.black.repository.PurchaseOrderRepository;
import bw.black.service.EmailService;
import bw.black.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final EmailService emailService;

    @Override
    public PurchaseOrderResponse uploadPo(MultipartFile file, PurchaseOrderRequest request) {
        try {
            byte[] fileBytes = file.getBytes();

            PurchaseOrder po = PurchaseOrder.builder()
                    .fileName(file.getOriginalFilename())
                    .fileData(fileBytes)
                    .companyName(request.getCompanyName())
                    .uploadedBy(request.getUploadedBy())
                    .uploadDate(LocalDate.now())
                    .accountantEmail(request.getAccountantEmail())
                    .build();

            PurchaseOrder saved = repository.save(po);

            // Mühasibə email göndər
            emailService.sendPoPdfByEmail(
                    saved.getAccountantEmail(),
                    saved.getCompanyName(),
                    saved.getUploadedBy(),
                    saved.getFileName(),
                    saved.getFileData()
            );

            return PurchaseOrderResponse.builder()
                    .id(saved.getId())
                    .fileName(saved.getFileName())
                    .companyName(saved.getCompanyName())
                    .uploadedBy(saved.getUploadedBy())
                    .uploadDate(saved.getUploadDate())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("PO faylını oxumaq mümkün olmadı", e);
        }
    }

    @Override
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = repository.findAll();

        return purchaseOrders.stream()
                .map(po -> PurchaseOrderResponse.builder()
                        .id(po.getId())
                        .fileName(po.getFileName())
                        .companyName(po.getCompanyName())
                        .uploadedBy(po.getUploadedBy())
                        .uploadDate(po.getUploadDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public byte[] getPoFileDataById(Long id) {
        PurchaseOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PurchaseOrder tapılmadı ID: " + id));
        return po.getFileData();
    }
}

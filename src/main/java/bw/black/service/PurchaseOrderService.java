package bw.black.service;

import bw.black.dto.request.PurchaseOrderRequest;
import bw.black.dto.response.PurchaseOrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PurchaseOrderService {
    PurchaseOrderResponse uploadPo(MultipartFile file, PurchaseOrderRequest request);
    List<PurchaseOrderResponse> getAllPurchaseOrders();

    byte[] getPoFileDataById(Long id);
}

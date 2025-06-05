package bw.black.controller;

import bw.black.dto.request.PurchaseOrderRequest;
import bw.black.dto.response.PurchaseOrderResponse;
import bw.black.service.PurchaseOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.util.List;

@RestController
@RequestMapping("/api/po")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/upload")
    public ResponseEntity<PurchaseOrderResponse> uploadPo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("request") String requestJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PurchaseOrderRequest request = mapper.readValue(requestJson, PurchaseOrderRequest.class);
        PurchaseOrderResponse response = purchaseOrderService.uploadPo(file, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getPoFile(@PathVariable Long id) {
        byte[] fileData = purchaseOrderService.getPoFileDataById(id);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"po_" + id + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileData);
    }


}

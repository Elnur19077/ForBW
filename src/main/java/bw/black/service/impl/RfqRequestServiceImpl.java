package bw.black.service.impl;

import bw.black.dto.request.RfqRequestRequest;
import bw.black.dto.response.RespStatus;
import bw.black.dto.response.Response;
import bw.black.dto.response.RfqRequestResponse;
import bw.black.entity.RfqRequest;
import bw.black.repository.RfqRequestRepository;
import bw.black.service.CloudinaryService;
import bw.black.service.RfqRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RfqRequestServiceImpl implements RfqRequestService {

    private final RfqRequestRepository repository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Response<RfqRequestResponse> createRfqRequest(RfqRequestRequest request) {
        Response<RfqRequestResponse> response = new Response<>();

        try {
            String pdfPath = null;
            if (request.getPdfFile() != null && !request.getPdfFile().isEmpty()) {
                pdfPath = cloudinaryService.uploadFile(request.getPdfFile());
            }

            String excelPath = null;
            if (request.getExcelFile() != null && !request.getExcelFile().isEmpty()) {
                excelPath = cloudinaryService.uploadFile(request.getExcelFile());
            }

            RfqRequest rfq = RfqRequest.builder()
                    .requestName(request.getRequestName())
                    .companyName(request.getCompanyName())
                    .deadline(request.getDeadline())
                    .submitted(request.isSubmitted())
                    .note(request.getNote())
                    .salesperson(request.getSalesperson())
                    .pdfPath(pdfPath)
                    .excelPath(excelPath)
                    .build();

            RfqRequest saved = repository.save(rfq);

            response.setT(mapToResponse(saved));
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(999, "Failed to create RFQ"));
        }
        return response;
    }

    @Override
    public Response<List<RfqRequestResponse>> getAllRfqRequests() {
        Response<List<RfqRequestResponse>> response = new Response<>();
        try {
            List<RfqRequestResponse> result = repository.findAll().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());

            if (result.isEmpty()) {
                response.setStatus(new RespStatus(2, "No RFQs found"));
            } else {
                response.setT(result);
                response.setStatus(RespStatus.getSuccessMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(999, "Error getting RFQs"));
        }
        return response;
    }

    @Override
    public Response<RfqRequestResponse> getRfqRequestById(Long id) {
        Response<RfqRequestResponse> response = new Response<>();
        try {
            RfqRequest rfq = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("RFQ not found"));
            response.setT(mapToResponse(rfq));
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(404, ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(999, "Internal error"));
        }
        return response;
    }

    @Override
    public Response<String> deleteRfqRequest(Long id) {
        Response<String> response = new Response<>();
        try {
            repository.deleteById(id);
            response.setT("Deleted successfully");
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(999, "Delete failed"));
        }
        return response;
    }

    private RfqRequestResponse mapToResponse(RfqRequest rfq) {
        return RfqRequestResponse.builder()
                .id(rfq.getId())
                .requestName(rfq.getRequestName())
                .companyName(rfq.getCompanyName())
                .deadline(rfq.getDeadline())
                .submitted(rfq.isSubmitted())
                .note(rfq.getNote())
                .salesperson(rfq.getSalesperson())
                .pdfPath(rfq.getPdfPath())
                .excelPath(rfq.getExcelPath())
                .build();
    }
}

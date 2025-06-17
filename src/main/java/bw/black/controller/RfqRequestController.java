package bw.black.controller;

import bw.black.dto.request.RfqRequestRequest;
import bw.black.dto.response.Response;
import bw.black.dto.response.RfqRequestResponse;
import bw.black.service.RfqRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rfq")
@RequiredArgsConstructor
public class RfqRequestController {

   private final  RfqRequestService rfqRequestService;

    @PostMapping("/create")
    public Response<RfqRequestResponse> create(@ModelAttribute RfqRequestRequest request) {
        return rfqRequestService.createRfqRequest(request);
    }

    @GetMapping
    public Response<List<RfqRequestResponse>> getAll() {
        return rfqRequestService.getAllRfqRequests();
    }

    @GetMapping("/{id}")
    public Response<RfqRequestResponse> getById(@PathVariable Long id) {
        return rfqRequestService.getRfqRequestById(id);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        return rfqRequestService.deleteRfqRequest(id);
    }
}


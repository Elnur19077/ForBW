package bw.black.controller;

import bw.black.dto.request.RfqRequestRequest;
import bw.black.dto.response.Response;
import bw.black.dto.response.RfqRequestResponse;
import bw.black.service.RfqRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rfq")
@RequiredArgsConstructor
public class RfqRequestController {

   private final  RfqRequestService rfqRequestService;


    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<RfqRequestResponse> create(
            @RequestParam("requestName") String requestName,
            @RequestParam("companyName") String companyName,
            @RequestParam("deadline") String deadlineStr,
            @RequestParam("submitted") boolean submitted,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "salesperson", required = false) String salesperson,
            @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile,
            @RequestParam(value = "excelFile", required = false) MultipartFile excelFile
    ) {
        LocalDateTime deadline = LocalDateTime.parse(deadlineStr);

        RfqRequestRequest request = new RfqRequestRequest();
        request.setRequestName(requestName);
        request.setCompanyName(companyName);
        request.setDeadline(deadline);
        request.setSubmitted(submitted);
        request.setNote(note);
        request.setSalesperson(salesperson);
        request.setPdfFile(pdfFile);
        request.setExcelFile(excelFile);

        return rfqRequestService.createRfqRequest(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping
    public Response<List<RfqRequestResponse>> getAll() {
        return rfqRequestService.getAllRfqRequests();
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping("/{id}")
    public Response<RfqRequestResponse> getById(@PathVariable Long id) {
        return rfqRequestService.getRfqRequestById(id);
    }
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        return rfqRequestService.deleteRfqRequest(id);
    }
}


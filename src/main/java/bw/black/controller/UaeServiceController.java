package bw.black.controller;

import bw.black.dto.request.UaeServiceRequest;
import bw.black.dto.response.UaeServiceResponse;
import bw.black.service.UaeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uae-service")
@RequiredArgsConstructor
public class UaeServiceController {
    private final UaeServiceService service;
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @PostMapping("/calculate")
    public UaeServiceResponse calculate(@RequestBody UaeServiceRequest request) {
        return service.calculate(request);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @PostMapping("/submit")
    public UaeServiceResponse submit(@RequestBody UaeServiceRequest request) {
        return service.submit(request);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @GetMapping
    public List<UaeServiceResponse> getAll() {
        return service.findAll();
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @GetMapping("/{id}")
    public UaeServiceResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")

    @PutMapping("/{id}")
    public UaeServiceResponse update(@PathVariable Long id, @RequestBody UaeServiceRequest request) {
        return service.update(id, request);
    }
}

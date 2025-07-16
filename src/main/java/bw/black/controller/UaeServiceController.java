package bw.black.controller;

import bw.black.dto.request.UaeServiceRequest;
import bw.black.dto.response.UaeServiceResponse;
import bw.black.service.UaeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uae-service")
@RequiredArgsConstructor
public class UaeServiceController {
    private final UaeServiceService service;

    @PostMapping("/calculate")
    public UaeServiceResponse calculate(@RequestBody UaeServiceRequest request) {
        return service.calculate(request);
    }

    @PostMapping("/submit")
    public UaeServiceResponse submit(@RequestBody UaeServiceRequest request) {
        return service.submit(request);
    }

    @GetMapping
    public List<UaeServiceResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UaeServiceResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public UaeServiceResponse update(@PathVariable Long id, @RequestBody UaeServiceRequest request) {
        return service.update(id, request);
    }
}

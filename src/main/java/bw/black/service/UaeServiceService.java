package bw.black.service;

import bw.black.dto.request.UaeServiceRequest;
import bw.black.dto.response.UaeServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UaeServiceService {
    UaeServiceResponse calculate(UaeServiceRequest request);
    UaeServiceResponse submit(UaeServiceRequest request);
    List<UaeServiceResponse> findAll();
    UaeServiceResponse findById(Long id);
    void delete(Long id);
    UaeServiceResponse update(Long id, UaeServiceRequest request);
}

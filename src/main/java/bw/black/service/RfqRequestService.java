package bw.black.service;
import bw.black.dto.request.RfqRequestRequest;
import bw.black.dto.response.Response;
import bw.black.dto.response.RfqRequestResponse;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface RfqRequestService {
    Response<RfqRequestResponse> createRfqRequest(RfqRequestRequest request);
    Response<List<RfqRequestResponse>> getAllRfqRequests();
    Response<RfqRequestResponse> getRfqRequestById(Long id);
    Response<String> deleteRfqRequest(Long id);
}

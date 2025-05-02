package bw.black.service;

import bw.black.dto.request.HrrRequest;
import bw.black.dto.response.HrrResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface HrrService {

    List<HrrResponse> getAttendance(HrrRequest request);
}
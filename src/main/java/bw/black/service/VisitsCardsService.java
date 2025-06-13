package bw.black.service;

import bw.black.dto.request.VisitCardsRequest;
import bw.black.dto.response.Response;
import bw.black.dto.response.VisitCardsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VisitsCardsService {
    Response<List<VisitCardsResponse>> getaAll();

    Response<VisitCardsResponse> findByID(Long id);

    Response<VisitCardsResponse> createVisit(VisitCardsRequest visitCardsRequest);

    Response<VisitCardsResponse> uptadeVisits(VisitCardsRequest visitCardsRequest);

    Response<VisitCardsResponse> deleteVisit(Long id);
}

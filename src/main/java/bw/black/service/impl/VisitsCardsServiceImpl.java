/*
package bw.black.service.impl;

import bw.black.dto.request.VisitCardsRequest;
import bw.black.dto.response.Response;
import bw.black.dto.response.VisitCardsResponse;
import bw.black.entity.Contacts;
import bw.black.entity.VisitCards;
import bw.black.exception.ContactsException;
import bw.black.exception.ExceptionConstant;
import bw.black.repository.VisitCardsRepository;
import bw.black.service.VisitsCardsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitsCardsServiceImpl implements VisitsCardsService {

    private  final VisitCardsRepository visitCardsRepository;

    @Override
    public Response<List<VisitCardsResponse>> getaAll() {
        Response<List<VisitCardsResponse>> response = new Response<>();
        try {
            List<VisitCards> contactss = visitCardsRepository.findAll();
            if (contactss.isEmpty()) {
                throw new ContactsException("No contacts found", ExceptionConstant.INTERNAL_EXCEPTION);
            }


            return null;
        }
    }

    @Override
    public Response<VisitCardsResponse> findByID(Long id) {
        return null;
    }

    @Override
    public Response<VisitCardsResponse> createVisit(VisitCardsRequest visitCardsRequest) {
        return null;
    }

    @Override
    public Response<VisitCardsResponse> uptadeVisits(VisitCardsRequest visitCardsRequest) {
        return null;
    }

    @Override
    public Response<VisitCardsResponse> deleteVisit(Long id) {
        return null;
    }

    public  VisitCardsResponse convertVists(VisitCards visitCards) {
        return  VisitCardsResponse.builder()
                .id(visitCards.getId())
                .name(visitCards.getName())
                .phone(visitCards.getPhone())
                .city(visitCards.getCity())
                .email(visitCards.getEmail())
                .company(visitCards.getCompany())
                .country(visitCards.getCountry())
                .email2(visitCards.getEmail2())
                .state(visitCards.getState())
                .jobTitle(visitCards.getJobTitle())
                .phone2(visitCards.getPhone2())
                .phone3(visitCards.getPhone3())
                .postalCode(visitCards.getPostalCode())
                .street(visitCards.getStreet())
                .website(visitCards.getWebsite())
                .build();
    }
}
*/
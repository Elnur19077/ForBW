package bw.black.service;

import bw.black.dto.request.ContactReq;
import bw.black.dto.response.ContactsResp;
import bw.black.dto.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactsService {
    Response<List<ContactsResp>> getAllContacts();

    Response<List<ContactsResp>> findByBrand(String brand);

    Response<List<ContactsResp>> findName(String company);

    Response<List<ContactsResp>> findByStakeholders(String stakeholders);

    Response<ContactsResp> findById(Long id);

    Response<ContactsResp> createContact(ContactReq contactReq);
}

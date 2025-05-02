package bw.black.service;

import bw.black.dto.request.ContactReq;
import bw.black.dto.response.ContactsResp;
import bw.black.dto.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Response<ContactsResp> createContact(ContactReq contactReq);
}

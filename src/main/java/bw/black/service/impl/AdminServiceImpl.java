package bw.black.service.impl;

import bw.black.dto.request.ContactReq;
import bw.black.dto.response.ContactsResp;
import bw.black.dto.response.RespStatus;
import bw.black.dto.response.Response;
import bw.black.entity.Contacts;
import bw.black.exception.ContactsException;
import bw.black.exception.ExceptionConstant;
import bw.black.repository.ContactsRepository;
import bw.black.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final ContactsRepository contactsRepository;

    @Override
    public Response<ContactsResp> createContact(ContactReq contactReq) {
        Response<ContactsResp> response = new Response<>();
        try {
            String address = contactReq.getAddress();
            String email = contactReq.getEmail();
            String phone = contactReq.getPhone();
            String stakeHolders = contactReq.getStakeHolders();
            if (address == null || email == null || phone == null || stakeHolders == null) {
                throw new ContactsException("Invalid data request", ExceptionConstant.INVALID_REQUEST_DATA);
            }
            Contacts contacts = Contacts.builder()
                    .address(address)
                    .brand(contactReq.getBrand())
                    .companyName(contactReq.getCompanyName())
                    .email(email)
                    .mobile(contactReq.getMobile())
                    .phone(phone)
                    .productRange(contactReq.getProductRange())
                    .stakeHolders(stakeHolders)
                    .website(contactReq.getWebsite())
                    .build();

            contactsRepository.save(contacts);
            ContactsResp contactsResp = convertContacts(contacts);
            response.setT(contactsResp);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (ContactsException e) {
            e.printStackTrace();
        }
        return response;
    }

    public ContactsResp convertContacts(Contacts contacts) {
        return ContactsResp.builder().
                companyName(contacts.getCompanyName()).
                mobile(contacts.getMobile()).
                email(contacts.getEmail()).
                address(contacts.getAddress()).
                stakeHolders(contacts.getStakeHolders()).
                brand(contacts.getBrand()).
                phone(contacts.getPhone()).
                productRange(contacts.getProductRange()).
                build();

    }
}

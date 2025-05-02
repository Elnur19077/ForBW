package bw.black.service.impl;

import bw.black.dto.request.ContactReq;
import bw.black.dto.response.ContactsResp;
import bw.black.dto.response.RespStatus;
import bw.black.dto.response.Response;
import bw.black.entity.Contacts;
import bw.black.exception.ContactsException;
import bw.black.exception.ExceptionConstant;
import bw.black.repository.ContactsRepository;
import bw.black.service.ContactsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContacsServiceImpl implements ContactsService {
    private final ContactsRepository contactsRepository;


    @Override
    public Response<List<ContactsResp>> getAllContacts() {
        Response<List<ContactsResp>> response = new Response<>();
        try {
            List<Contacts> contactss = contactsRepository.findAll();
            if (contactss.isEmpty()) {
                throw new Exception("No contacts found");
            }
            List<ContactsResp> contactsResps = contactss.stream().map(this::convertContacts).toList();
            response.setT(contactsResps);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Response<List<ContactsResp>> findByBrand(String brand) {
        Response<List<ContactsResp>> response = new Response<>();
        try {
            List<Contacts> contacts = contactsRepository.findAllByBrand(brand);
            if (contacts.isEmpty()) {
                throw new Exception("No brands found");
            }
            List<ContactsResp> contactsResps = contacts.stream().map(this::convertContacts).toList();
            response.setT(contactsResps);
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public Response<List<ContactsResp>> findName(String company) {
        Response<List<ContactsResp>> response = new Response<>();
        try {
            List<Contacts> contacts = contactsRepository.findAllByCompanyName(company);
            if (contacts.isEmpty()) {
                throw new ContactsException("Company not founded", ExceptionConstant.CONTACTS_NOT_FOUND);
            }
            List<ContactsResp> contactsResps = contacts.stream().map(this::convertContacts).toList();
            response.setT(contactsResps);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (ContactsException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<List<ContactsResp>> findByStakeholders(String stakeholders) {
        Response<List<ContactsResp>> findByStakeholders = new Response<>();
        try {
            List<Contacts> stakeHolders = contactsRepository.findAllByStakeHolders(stakeholders);
            if (stakeHolders.isEmpty()) {
                throw new ContactsException("Stakeholder not founded", ExceptionConstant.CONTACTS_NOT_FOUND);
            }
            List<ContactsResp> contactsResps = stakeHolders.stream().map(this::convertContacts).toList();
            findByStakeholders.setT(contactsResps);
            findByStakeholders.setStatus(RespStatus.getSuccessMessage());

        } catch (ContactsException e) {
            e.printStackTrace();
        }
        return findByStakeholders;
    }

    @Override
    public Response<ContactsResp> findById(Long id) {
        return null;
    }

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
                    .id(contactReq.getId())
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
                id(contacts.getId()).
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

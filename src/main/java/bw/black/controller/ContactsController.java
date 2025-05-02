package bw.black.controller;

import bw.black.dto.request.ContactReq;
import bw.black.dto.response.ContactsResp;
import bw.black.dto.response.Response;
import bw.black.service.ContactsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactsService contactsService;
    @GetMapping("/getAllContacts")
     public Response<List<ContactsResp>> getAllContacts () {
        return  contactsService.getAllContacts() ;
    }
    @PostMapping("/create")
    Response<ContactsResp> createContact(@RequestBody ContactReq contactReq) {
        return contactsService.createContact(contactReq);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllContactss")
    public Response<List<ContactsResp>> getAllContacts(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5501"); // İzin verilen frontend adresi
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        return contactsService.getAllContacts();
    }
    @GetMapping("/findbrand/{brand}")
    public Response<List<ContactsResp>> findByBrand (@PathVariable String brand) {
        return  contactsService.findByBrand(brand);
    }

    @GetMapping("/findName/{company}")
    public Response<List<ContactsResp>> findByName (@PathVariable String company) {
        return contactsService.findName(company);
    }

    @GetMapping("/findStakeholder/{stakeholders}")
    public Response<List<ContactsResp>> findByStakeholders (@PathVariable String stakeholders) {
        return  contactsService.findByStakeholders(stakeholders);
    }
    @GetMapping("/findByID/{Id}")
    public Response<ContactsResp> findById (@PathVariable Long Id) {
        return contactsService.findById(Id);
    }
}

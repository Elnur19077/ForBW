package bw.black.controller;

import bw.black.dto.request.ContactReq;
import bw.black.dto.response.ContactsResp;
import bw.black.dto.response.Response;
import bw.black.service.AdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
  private  final AdminService adminService;

  //@PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
  @PostMapping("/create")
  Response<ContactsResp> createContact(@RequestBody ContactReq  contactReq) {
    return adminService.createContact(contactReq);
  }

}

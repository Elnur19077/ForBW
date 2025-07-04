package bw.black.controller;

import bw.black.dto.request.HrrRequest;
import bw.black.dto.response.HrrResponse;
import bw.black.service.HrrService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/hrr")
public class HrrController {
    private final HrrService hrrService;


    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping("/attendance")
    public ResponseEntity<List<HrrResponse>> getAttendance(@RequestBody HrrRequest request) {
        List<HrrResponse> responses = hrrService.getAttendance(request);
        return ResponseEntity.ok(responses);
    }
}

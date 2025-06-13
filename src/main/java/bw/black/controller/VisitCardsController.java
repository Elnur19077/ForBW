package bw.black.controller;

import bw.black.dto.request.VisitCardsRequest;
import bw.black.dto.response.Response;
import bw.black.dto.response.VisitCardsResponse;
import bw.black.service.VisitsCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitCardsController {
    private final VisitsCardsService visitsCardsService;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping("/all")
    Response<List<VisitCardsResponse>> allVisitCards() {
        return visitsCardsService.getaAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping("/findByID/{id}")
    Response<VisitCardsResponse> visitCardsByID(@PathVariable Long id) {
        return visitsCardsService.findByID(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping("/create")
    Response<VisitCardsResponse> createVisitCards(@RequestBody VisitCardsRequest visitCardsRequest) {
        return visitsCardsService.createVisit(visitCardsRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PutMapping("/uptade")
    Response<VisitCardsResponse> updateVisitCards(@RequestBody VisitCardsRequest visitCardsRequest) {
        return visitsCardsService.uptadeVisits(visitCardsRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PutMapping("/delete/{id}")
    Response<VisitCardsResponse> deleteVisitCards(@PathVariable Long id) {
  return visitsCardsService.deleteVisit(id);
    }


}

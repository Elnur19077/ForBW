package bw.black.controller;




import bw.black.dto.request.NoteRequest;
import bw.black.dto.response.NoteResponse;
import bw.black.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @PostMapping
    public ResponseEntity<bw.black.dto.response.NoteResponse> createNote(@RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.createNote(request));
    }
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')or hasAuthority('OPERATOR')")
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getMyNotes() {
        return ResponseEntity.ok(noteService.getMyNotes());
    }
}

package bw.black.service;

import bw.black.dto.request.NoteRequest;
import bw.black.dto.response.NoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    NoteResponse createNote(NoteRequest request);
    List<NoteResponse> getMyNotes();
}

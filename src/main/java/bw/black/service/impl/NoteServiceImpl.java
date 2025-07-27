package bw.black.service.impl;

import bw.black.dto.request.NoteRequest;
import bw.black.dto.response.NoteResponse;
import bw.black.entity.Employee;
import bw.black.entity.Note;
import bw.black.repository.EmployeeRepository;
import bw.black.repository.NoteRepository;
import bw.black.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public NoteResponse createNote(NoteRequest request) {
        String email = getCurrentUserEmail();
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        Note note = Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .employee(employee)
                .build();

        note = noteRepository.save(note);

        return mapToResponse(note);
    }

    @Override
    public List<NoteResponse> getMyNotes() {
        String email = getCurrentUserEmail();
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        List<Note> notes = noteRepository.findByEmployee(employee);

        return notes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private NoteResponse mapToResponse(Note note) {
        NoteResponse response = new NoteResponse();
        response.setId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getContent());
        return response;
    }
}

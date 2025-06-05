package bw.black.service.impl;

import bw.black.repository.NoteRepository;
import bw.black.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {
    private  final NoteRepository noteRepository;


}

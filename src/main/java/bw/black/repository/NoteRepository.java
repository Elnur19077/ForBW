package bw.black.repository;

import bw.black.entity.Employee;
import bw.black.entity.Note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEmployee(Employee employee);



}

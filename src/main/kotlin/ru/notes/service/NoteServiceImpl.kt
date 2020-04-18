package ru.notes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import ru.notes.exception.NoteServiceException
import ru.notes.model.Note
import java.time.LocalDateTime

@Service
class NoteServiceImpl
@Autowired
constructor(private val noteRepository: ru.notes.repository.NoteRepository) : NoteService {
    
    @GetMapping
    override fun getAll(pageable: Pageable): Page<Note> {
        return noteRepository.findAll(pageable)
    }

    @GetMapping("{id}")
    override fun getById(note: Note): Note {
        return note
    }

    @PostMapping
    override fun add(note: Note): Note {
        if (isExist(note))
           throw NoteServiceException(message = "Product with id exist")
        note.createDate = LocalDateTime.now()
        return noteRepository.save(note)
    }

    @PutMapping("{id}")
    override fun update(note: Note): Note {
        return noteRepository.save( note)
    }

    @DeleteMapping("{id}")
    override fun delete(note: Note) {
        noteRepository.delete(note)
    }

    override fun isExist(note: Note): Boolean {
        return noteRepository.findAll()
                .map { it.id }
                .contains(note.id)
    }
}
package ru.notes.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.notes.exception.NoteServiceException
import ru.notes.model.Note

@Service
interface NoteService {

    @Throws(NoteServiceException::class)
    fun getAll(pageable: Pageable): Page<Note>

    @Throws(NoteServiceException::class)
    fun get(id: Long): Note

    @Throws(NoteServiceException::class)
    fun add(note: Note): Note

    @Throws(NoteServiceException::class)
    fun update(note: Note): Note

    @Throws(NoteServiceException::class)
    fun delete(id: Long)
}
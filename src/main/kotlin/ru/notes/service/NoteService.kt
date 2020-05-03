package ru.notes.service

import org.springframework.stereotype.Service
import ru.notes.dto.NoteDtoIn
import ru.notes.dto.NoteDtoOut
import ru.notes.exception.NoteServiceException

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Service
interface NoteService {

    @Throws(NoteServiceException::class)
    fun getAllNotes(): List<NoteDtoOut>?

    @Throws(NoteServiceException::class)
    fun getNoteOutById(id: Long): NoteDtoOut

    @Throws(NoteServiceException::class)
    fun addNote(noteDto: NoteDtoIn): NoteDtoOut

    @Throws(NoteServiceException::class)
    fun updateNote(id: Long, noteDto: NoteDtoIn): NoteDtoOut

    @Throws(NoteServiceException::class)
    fun deleteNote(id: Long)

    @Throws(NoteServiceException::class)
    fun findNotes(text: String): List<NoteDtoOut>?
}
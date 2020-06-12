package ru.notes.service

import ru.notes.dto.NoteDtoIn
import ru.notes.dto.NoteDtoOut
import ru.notes.exception.NoteServiceException

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */

interface NoteService {

    @Throws(NoteServiceException::class)
    fun getAllNotes(): List<NoteDtoOut>?

    @Throws(NoteServiceException::class)
    fun getNote(id: Long): NoteDtoOut

    @Throws(NoteServiceException::class)
    fun addNote(noteDtoIn: NoteDtoIn): NoteDtoOut

    @Throws(NoteServiceException::class)
    fun updateNote(id: Long, noteDtoOut: NoteDtoOut): NoteDtoOut

    @Throws(NoteServiceException::class)
    fun deleteNote(id: Long): Boolean

    @Throws(NoteServiceException::class)
    fun findNotes(text: String): List<NoteDtoOut>?
}
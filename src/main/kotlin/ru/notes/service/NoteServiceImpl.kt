package ru.notes.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.notes.exception.NotFoundException
import ru.notes.exception.NoteServiceException
import ru.notes.model.Note
import ru.notes.repository.NoteRepository
import java.time.LocalDateTime

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Service
class NoteServiceImpl
@Autowired constructor(
        private val noteRepository: NoteRepository
) : NoteService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(NoteServiceImpl::class.java)!!
    }

    @Transactional(readOnly = true)
    override fun getAll(pageable: Pageable): Page<Note> {
        return noteRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    override fun get(id: Long): Note =
            noteRepository
                    .findById(id)
                    .orElseThrow {
                        log.error("Note with id: $id not found")
                        NotFoundException(id)
                    }


    @Transactional
    override fun add(note: Note): Note {
        if (noteRepository.existsById(note.id)) {
            val message = "Note with id: " + note.id + " already exists"
            log.error(message)
            throw NoteServiceException(message)
        }
        note.createDate = LocalDateTime.now()
        note.modifyDate = LocalDateTime.now()
        return noteRepository.save(note)
    }

    @Transactional
    override fun update(note: Note): Note {
        if (!noteRepository.existsById(note.id)) {
            val message = "Note with id: " + note.id + " not found"
            log.error(message)
            throw NotFoundException(note.id)
        }
        note.modifyDate = LocalDateTime.now()
        return noteRepository.save(note)
    }

    @Transactional
    override fun delete(id: Long) =
            noteRepository.delete(get(id))
}
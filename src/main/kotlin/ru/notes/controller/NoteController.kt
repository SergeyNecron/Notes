package ru.notes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import ru.notes.exception.NoteServiceException
import ru.notes.model.Note
import ru.notes.service.NoteService

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@RestController
@RequestMapping("/note")
class NoteController @Autowired constructor(
        private val noteService: NoteService
) {
    @GetMapping
    @Throws(NoteServiceException::class)
    fun getAll(pageable: Pageable): Page<Note> {
        return noteService.getAll(pageable)
    }

    @GetMapping("{id}")
    @Throws(NoteServiceException::class)
    fun getById(@PathVariable("id") id: Long): Note {
        return noteService.get(id)
    }

    @PostMapping
    @Throws(NoteServiceException::class)
    fun add(@RequestBody note: Note): ResponseEntity<Note> =
            ok(noteService.add(note))

    @PutMapping
    @Throws(NoteServiceException::class)
    fun update(@RequestBody note: Note): Note {
        return noteService.update(note)
    }

    @DeleteMapping("{id}")
    @Throws(NoteServiceException::class)
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Boolean> {
        noteService.delete(id)
        return ok(true)
    }
}
package ru.notes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import ru.notes.model.Note
import ru.notes.service.NoteService

@RestController
@RequestMapping("/note")
class NoteController @Autowired constructor(
        private val noteService: NoteService
) {
    @GetMapping
    fun getAll(@PageableDefault pageable: Pageable): Page<Note> {
        return noteService.getAll(pageable)
    }

    @GetMapping("{id}")
    fun getById(@PathVariable("id") note: Note): Note {
        return note
    }

    @PostMapping
    fun add(@RequestBody note: Note): ResponseEntity<Note> =
            ok(noteService.add(note))

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody note: Note): Note {
        note.id = id
        return noteService.update(note)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") note: Note) {
        noteService.delete(note)
    }
}
package ru.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.notes.model.Note

@Repository
interface NoteRepository : JpaRepository<Note, Long>
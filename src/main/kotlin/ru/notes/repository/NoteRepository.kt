package ru.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.notes.model.Note

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Repository
interface NoteRepository : JpaRepository<Note, Long>
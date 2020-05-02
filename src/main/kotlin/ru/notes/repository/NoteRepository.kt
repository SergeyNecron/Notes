package ru.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.notes.model.Note

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    @Query("from Note n where " +
            "concat(n.title, ' ', n.tag, ' ', n.description) like concat('%', :name, '%')")
    fun findByName(@Param("name") name: String): List<Note>?
}
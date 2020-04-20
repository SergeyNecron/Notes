package ru.notes.model

import ru.notes.dto.NoteDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Entity
data class Note(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        var id: Long,
        var title: String,
        var tag: String?,
        var description: String?,
        var createDate: LocalDateTime?,
        var modifyDate: LocalDateTime?
) {
    constructor(noteDto: NoteDto)
            : this(0L,// Generated
            noteDto.title,
            noteDto.tag,
            noteDto.description,
            LocalDateTime.now(),
            LocalDateTime.now()
    )

    fun updateFromDto(noteDto: NoteDto): Note {
        this.title = noteDto.title
        this.tag = noteDto.tag
        this.description = noteDto.description
        return this
    }
}
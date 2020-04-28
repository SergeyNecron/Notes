package ru.notes.model

import ru.notes.dto.NoteDto
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Entity
data class Note(
        var title: String?,
        var tag: String?,
        var description: String?,
        @ManyToOne(fetch = FetchType.LAZY)
        private val user: User? = null
) : AbstractEntity() {

    constructor(noteDto: NoteDto) : this(
            noteDto.title,
            noteDto.tag,
            noteDto.description
    )

    fun updateFromDto(noteDto: NoteDto): Note {
        this.title = noteDto.title
        this.tag = noteDto.tag
        this.description = noteDto.description
        this.modifyDate = LocalDateTime.now()
        return this
    }
}
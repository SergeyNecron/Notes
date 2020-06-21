package ru.notes.model

import ru.notes.dto.NoteDtoOut
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
        var title: String = "",
        var tag: String = "",
        var description: String = "",
        @ManyToOne(fetch = FetchType.LAZY)
        private val user: User? = null
) : BaseEntity() {

    constructor(dto: NoteDtoOut) : this(
            dto.title,
            dto.tag,
            dto.description
    )

    fun updateFromDto(dto: NoteDtoOut): Note {
        this.title = dto.title
        this.tag = dto.tag
        this.description = dto.description
        this.modifyDate = LocalDateTime.now()
        return this
    }
}
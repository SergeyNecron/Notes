package ru.notes.dto

import ru.notes.model.Note
import java.time.LocalDateTime

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-05-03
 */
class NoteDtoOut(val id: Long?,
                 val title: String?,
                 val tag: String?,
                 val description: String?,
                 val createDate: LocalDateTime?,
                 val modifyDate: LocalDateTime?) {

    constructor() : this(null, null, null, null, null, null)

    constructor(note: Note) : this(
            note.id,
            note.title,
            note.tag,
            note.description,
            note.createDate,
            note.modifyDate
    )
}
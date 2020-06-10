package ru.notes.dto

import ru.notes.model.Note
import java.time.LocalDateTime

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-05-03
 */
class NoteDtoOut(val id: Long,
                 var title: String?,
                 var tag: String?,
                 var description: String?,
                 val createDate: LocalDateTime?,
                 val modifyDate: LocalDateTime?) {

    constructor() : this(0L, null, null, null, null, null)

    constructor(note: Note) : this(
            note.id,
            note.title,
            note.tag,
            note.description,
            note.createDate,
            note.modifyDate
    )
}
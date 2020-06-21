package ru.notes.dto

import ru.notes.model.Note

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-05-03
 */
data class NoteDtoOut(
        val id: Long,
        var title: String = "",
        var tag: String = "",
        var description: String = ""
) {

    constructor() : this(0L, "", "", "")

    constructor(note: Note) : this(
            note.id,
            note.title,
            note.tag,
            note.description
    )
}
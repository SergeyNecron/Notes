package ru.notes.dto

import ru.notes.model.Note

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-20
 */
data class NoteDtoIn(
        private val title: String = "",
        private val tag: String = "",
        private val description: String = ""
) {

    constructor(dto: NoteDtoOut) : this(
            dto.title,
            dto.tag,
            dto.description
    )

    fun convertToNote() = Note(
            title,
            tag,
            description
    )
}
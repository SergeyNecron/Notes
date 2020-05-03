package ru.notes.dto

import ru.notes.model.Note

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-20
 */
class NoteDtoIn
constructor(val title: String,
            val tag: String?,
            val description: String?
) {
    fun convertToNote(): Note =
            Note(this.title,
                    this.tag,
                    this.description
            )
}
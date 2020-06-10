package ru.notes.dto

import ru.notes.model.Note

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-20
 */
class NoteDtoIn(
        private val title: String?,
        private val tag: String?,
        private val description: String?
) {
    fun convertToNote(): Note =
            Note(title,
                    tag,
                    description
            )
}
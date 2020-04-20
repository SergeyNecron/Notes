package ru.notes.dto

import com.fasterxml.jackson.annotation.JsonCreator
import java.time.LocalDateTime

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-20
 */
class NoteDto
constructor(val title: String,
            val tag: String?,
            val description: String?)
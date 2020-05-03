package ru.notes.dto

import java.time.LocalDateTime

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-05-03
 */
class NoteDtoOut(val title: String?,
                 val tag: String?,
                 val description: String?,
                 val createDate: LocalDateTime,
                 var modifyDate: LocalDateTime)
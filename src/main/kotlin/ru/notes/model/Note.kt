package ru.notes.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@Entity
data class Note(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        var id: Long,
        var title: String,
        var tag: String?,
        var description: String?,
        var createDate: LocalDateTime?,
        var modifyDate: LocalDateTime?) {
    constructor(id: Long, title: String)
            : this(id, title, null, null, null, null)
}
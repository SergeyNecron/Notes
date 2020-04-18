package ru.notes.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Note(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        val id: Long,
        val createDate: LocalDateTime,
        var title: String,
        var tag: String,
        var description: String,
        var modifyDate: LocalDateTime)
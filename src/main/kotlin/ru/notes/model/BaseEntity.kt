package ru.notes.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import ru.notes.config.DATE_FORMAT
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @JsonIgnore
    val id: Long = -1

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Column(name = "create_date")
    val createDate: LocalDateTime = LocalDateTime.now()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Column(name = "modify_date")
    var modifyDate: LocalDateTime = LocalDateTime.now()
}
package ru.notes.model

import ru.notes.dto.UserDtoOut
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Table

@Entity
//syntax error at or near "user"
@Table(name = "USER_NOTE")
data class User(
        var firstname: String = "",  // переименовать
        var lastname: String = "",
        var patronymic: String = "",
//        var login: String = "",      //добавить chengeset в базу
        var email: String = "",
        var password: String = "",
        var enabled: Boolean = true
//        ,
//        @OneToMany(mappedBy = "user",
//                fetch = FetchType.LAZY,
//                cascade = [CascadeType.ALL],
//                orphanRemoval = true)
//        var notes: Set<Note>
) : BaseEntity() {

    fun updateFromDto(dto: UserDtoOut): User {
        this.firstname = dto.firstName
        this.lastname = dto.lastName
        this.patronymic = dto.patronymic
//        this.login = dto.login
        this.email = dto.email
        this.enabled = dto.enabled
        this.modifyDate = LocalDateTime.now()
        return this
    }
}

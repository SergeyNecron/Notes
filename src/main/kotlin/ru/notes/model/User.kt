package ru.notes.model

import ru.notes.dto.UserDto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
//syntax error at or near "user"
@Table(name = "USER_NOTE")
data class User(
        var firstname: String? = null,
        var lastname: String? = null,
        var patronymic: String? = null,
        var email: String? = null,
        var password: String? = null,
        var enabled: Boolean = true,
        @OneToMany(mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                orphanRemoval = true)
        var notes: Set<Note>
) : BaseEntity() {

    constructor(userDto: UserDto) : this(
            userDto.firstname,
            userDto.lastname,
            userDto.patronymic,
            userDto.email,
            userDto.password,
            true,
            HashSet<Note>()
    )

    fun updateFromDto(userDto: UserDto): User {
        this.firstname = userDto.firstname
        this.firstname = userDto.lastname
        this.firstname = userDto.patronymic
        this.email = userDto.email
        this.password = userDto.password
        this.modifyDate = LocalDateTime.now()
        return this
    }
}

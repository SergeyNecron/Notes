package ru.notes.model

import ru.notes.dto.UserDto
import java.time.LocalDateTime
import javax.persistence.*

@Entity
//syntax error at or near "user"
@Table(name = "USER_NOTE")
data class User(
        var name: String? = null,
        var email: String? = null,
        var password: String? = null,
        var enabled: Boolean = true,
        @OneToMany(mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                orphanRemoval = true)
        var notes: Set<Note>
) : AbstractEntity() {

        constructor(userDto: UserDto) : this(
                userDto.name,
                userDto.email,
                userDto.password,
                true,
                HashSet<Note>()
        )

        fun updateFromDto(userDto: UserDto): User {
                this.name = userDto.name
                this.email = userDto.email
                this.password = userDto.password
                this.modifyDate = LocalDateTime.now()
                return this
        }
}

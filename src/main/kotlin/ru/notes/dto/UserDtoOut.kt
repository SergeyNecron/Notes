package ru.notes.dto

import ru.notes.model.User

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
data class UserDtoOut(
        val id: Long,
        var firstName: String = "",
        var lastName: String = "",
        var patronymic: String = "",
//        val login: String = "",
//        @Email(message = "правильный e-mail")
        var email: String = "",
        var enabled: Boolean
) {

    constructor() : this(0L, "", "", "", "", false)

    constructor(user: User) : this(
            user.id,
            user.firstname,
            user.lastname,
            user.patronymic,
//            user.login,
            user.email,
            user.enabled
    )
}
package ru.notes.dto

import ru.notes.model.User

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
data class UserDtoIn(
        var firstName: String = "",
        var lastName: String = "",
        var patronymic: String = "",
//       var login: String = "",
        var password: String = "",
        var repeatPassword: String = "",
        var email: String = ""
) {

    constructor(dto: UserDtoOut) : this(
            dto.firstName,
            dto.lastName,
            dto.patronymic,
//            dto.login,
            "",
            "",
            dto.email
    )

    fun convertToUser() = User(
            firstName,
            lastName,
            patronymic,
//            login,
            email,
            password,
            true
    )
}
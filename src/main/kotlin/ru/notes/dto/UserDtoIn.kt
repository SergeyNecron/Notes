package ru.notes.dto

import ru.notes.model.User

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
class UserDtoIn(
        val firstname: String?,
        val lastname: String?,
        val patronymic: String?,
        val email: String?,
        val password: String?
) {

    constructor(dto: UserDtoOut) : this(
            dto.firstname,
            dto.lastname,
            dto.patronymic,
            dto.email,
            dto.password
    )

    fun convertToUser(): User =
            User(firstname,
                    lastname,
                    patronymic,
                    email,
                    password
            )
}
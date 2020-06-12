package ru.notes.dto

import ru.notes.model.User
import java.time.LocalDateTime

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
class UserDtoOut
constructor(
        val id: Long,
        var firstname: String?,
        var lastname: String?,
        var patronymic: String?,
        var email: String?,
        var password: String?,
        val createDate: LocalDateTime?,
        val modifyDate: LocalDateTime?) {

    constructor() : this(0L, null, null, null, null, null, null, null)

    constructor(user: User) : this(
            user.id,
            user.firstname,
            user.lastname,
            user.patronymic,
            user.email,
            user.password,
            user.createDate,
            user.modifyDate
    )
}
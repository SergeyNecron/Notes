package ru.notes.service

import ru.notes.dto.UserDtoIn
import ru.notes.dto.UserDtoOut
import ru.notes.exception.NoteServiceException
import ru.notes.exception.UserServiceException

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */

interface UserService {

    @Throws(UserServiceException::class)
    fun getAll(): List<UserDtoOut>

    @Throws(UserServiceException::class)
    fun get(id: Long): UserDtoOut

    @Throws(UserServiceException::class)
    fun add(userDto: UserDtoIn): UserDtoOut

    @Throws(UserServiceException::class)
    fun update(id: Long, userDtoOut: UserDtoOut): UserDtoOut

    @Throws(UserServiceException::class)
    fun delete(id: Long): Boolean

    @Throws(NoteServiceException::class)
    fun findUsers(text: String): List<UserDtoOut>?
}
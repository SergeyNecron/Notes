package ru.notes.service

import org.springframework.stereotype.Service
import ru.notes.dto.UserDto
import ru.notes.exception.NoteServiceException
import ru.notes.exception.UserServiceException
import ru.notes.model.User

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
@Service
interface UserService {

    @Throws(UserServiceException::class)
    fun getAll(): List<User>

    @Throws(UserServiceException::class)
    fun get(id: Long): User

    @Throws(UserServiceException::class)
    fun add(userDto: UserDto): User

    @Throws(UserServiceException::class)
    fun update(id: Long, userDto: UserDto): User

    @Throws(UserServiceException::class)
    fun delete(id: Long)

    @Throws(NoteServiceException::class)
    fun findUsers(text: String): List<User>?
}
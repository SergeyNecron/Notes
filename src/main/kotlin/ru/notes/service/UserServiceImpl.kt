package ru.notes.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.notes.dto.UserDtoIn
import ru.notes.dto.UserDtoOut
import ru.notes.exception.NotFoundException
import ru.notes.model.User
import ru.notes.repository.UserRepository

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
@Service
class UserServiceImpl
@Autowired constructor(
        private val userRepository: UserRepository
) : UserService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)!!
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<UserDtoOut> {
        return userRepository
                .findAll()
                .map { UserDtoOut(it) }
                .sortedBy { it.id }
    }

    @Transactional(readOnly = true)
    override fun get(id: Long): UserDtoOut =
            UserDtoOut(getUserById(id))

    @Transactional
    override fun add(userDtoIn: UserDtoIn): UserDtoOut =
            UserDtoOut(userRepository.save(userDtoIn.convertToUser()))

    @Transactional
    override fun update(id: Long, userDtoOut: UserDtoOut): UserDtoOut =
            UserDtoOut(userRepository.save(getUserById(id).updateFromDto(userDtoOut)))

    @Transactional
    override fun delete(id: Long): Boolean {
        userRepository.delete(getUserById(id))
        return true
    }

    override fun findUsers(text: String): List<UserDtoOut>? {
        return if (text.isEmpty())
            getAll() else
            userRepository.findByName(text)
                    ?.map { UserDtoOut(it) }
                    ?.sortedBy { it.id }
    }

    fun getUserById(id: Long): User =
            userRepository
                    .findById(id)
                    .orElseThrow {
                        log.error("User with id: $id not found")
                        NotFoundException(id)
                    }
}
package ru.notes.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.notes.dto.UserDto
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
    override fun getAll(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    override fun get(id: Long): User =
            userRepository
                    .findById(id)
                    .orElseThrow {
                        log.error("User with id: $id not found")
                        NotFoundException(id)
                    }


    @Transactional
    override fun add(userDto: UserDto): User =
            userRepository.save(User(userDto))

    @Transactional
    override fun update(id: Long, userDto: UserDto): User =
            userRepository.save(get(id).updateFromDto(userDto))

    @Transactional
    override fun delete(id: Long) =
            userRepository.delete(get(id))
}
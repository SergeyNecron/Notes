package ru.notes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import ru.notes.dto.UserDto
import ru.notes.exception.UserServiceException
import ru.notes.model.User
import ru.notes.service.UserService

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
        private val userService: UserService
) {
    @GetMapping
    @Throws(UserServiceException::class)
    fun getAll(pageable: Pageable): Page<User> {
        return userService.getAll(pageable)
    }

    @GetMapping("/{id}")
    @Throws(UserServiceException::class)
    fun getById(@PathVariable("id") id: Long): User {
        return userService.get(id)
    }

    @PostMapping
    @Throws(UserServiceException::class)
    fun add(@RequestBody userDto: UserDto): ResponseEntity<User> =
            ok(userService.add(userDto))

    @PutMapping("/{id}")
    @Throws(UserServiceException::class)
    fun update(@PathVariable("id") id: Long,
               @RequestBody userDto: UserDto): User {
        return userService.update(id, userDto)
    }

    @DeleteMapping("/{id}")
    @Throws(UserServiceException::class)
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Boolean> {
        userService.delete(id)
        return ok(true)
    }
}
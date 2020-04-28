package ru.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.notes.model.User

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
@Repository
interface UserRepository : JpaRepository<User, Long>
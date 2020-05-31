package ru.notes.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.notes.model.User

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("from User u where " +
            "concat(u.firstname, ' ', u.lastname, ' ', u.patronymic) like concat('%', :name, '%')")
    fun findByName(@Param("name") name: String): List<User>?
}
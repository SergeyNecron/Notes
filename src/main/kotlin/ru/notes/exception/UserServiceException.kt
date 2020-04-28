package ru.notes.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-28
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
open class UserServiceException(message: String) : RuntimeException(message)
package ru.notes.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
open class NoteServiceException(message: String) : RuntimeException(message)
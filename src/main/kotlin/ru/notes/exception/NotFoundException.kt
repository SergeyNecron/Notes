package ru.notes.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-20
 * Time: 09:40
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
class NotFoundException(noteId: Long) : NoteServiceException("Unknown (id='$noteId')")
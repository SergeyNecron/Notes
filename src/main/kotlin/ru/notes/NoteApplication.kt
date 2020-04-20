package ru.notes

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 * @author Sergey Muratkin
 * Date: 2020-04-18
 */
@SpringBootApplication
class NotesApplication

fun main(args: Array<String>) {
    runApplication<NotesApplication>(*args)
}

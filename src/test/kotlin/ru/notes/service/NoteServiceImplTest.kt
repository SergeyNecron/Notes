package ru.notes.service

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.notes.model.Note
import ru.notes.repository.NoteRepository

internal class NoteServiceImplTest {

    @Mock
    private lateinit var repository: NoteRepository

    @InjectMocks
    private lateinit var service: NoteServiceImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldReturnTrue() {
        fillRepository()
        val product = Note(5, "Коммит в другую ветку")
        Assert.assertTrue(service.isExist(product))
    }

    @Test
    fun shouldReturnFalse() {
        fillRepository()
        val product = Note(9, "bpm")
        Assert.assertFalse(service.isExist(product))
    }

    private fun fillRepository() {
        val products = mutableListOf(
                Note(5, "Проброс папки"),
                Note(8, "Задача")
        )
        Mockito.`when`(repository.findAll()).thenReturn(products)
    }
}
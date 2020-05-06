package ru.notes.view

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.Route
import ru.notes.component.NoteEditor
import ru.notes.model.Note
import ru.notes.repository.NoteRepository


@Route("")
class MainView(private val noteRepository: NoteRepository,
               private val noteEditor: NoteEditor
) : VerticalLayout() {
    private val filter = TextField()
    private val addNewNoteButton = Button("New note", VaadinIcon.PLUS.create())
    private val toolbar = HorizontalLayout(filter, addNewNoteButton)
    private val grid = Grid(Note::class.java)

    init {
        initFilter()
        editNoteListener() //connect selected line to editor or hide if none is selected
        addNoteListener()  //edit new Note the new button is clicked
        refreshDataFromBackend()
        listNotes("")
        add(toolbar, grid, noteEditor)
    }

    private fun refreshDataFromBackend() {
        noteEditor.setChangeHandler(object : NoteEditor.ChangeHandler {
            override fun onChange() {
                noteEditor.isVisible = false
                listNotes(filter.value)
            }
        })
    }

    private fun addNoteListener() {
        addNewNoteButton.addClickListener {
            noteEditor.editNote(Note(null, null, null))
        }
    }

    private fun editNoteListener() {
        grid.asSingleSelect()
                .addValueChangeListener {
                    noteEditor.editNote(it.value)
                }
    }

    private fun initFilter() {
        filter.placeholder = "filter"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener {
            listNotes(it.value)
        }
    }

    private fun listNotes(name: String) {
        grid.setItems(
                if (name.isEmpty())
                    noteRepository.findAll() else
                    noteRepository.findByName(name)
        )
    }
}
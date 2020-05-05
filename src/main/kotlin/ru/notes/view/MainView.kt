package ru.notes.view

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent
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

    // build layout
    private val toolbar = HorizontalLayout(filter, addNewNoteButton)
    private val grid = Grid(Note::class.java)
    private fun notesList(name: String) {
        if (name.isEmpty()) {
            grid.setItems(noteRepository.findAll())
        } else {
            grid.setItems(noteRepository.findByName(name))
        }
    }

    init {
        filter.placeholder = "filter"
        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener { field: ComponentValueChangeEvent<TextField?, String> -> notesList(field.value) }
        add(toolbar, grid, noteEditor)
        // Connect selected Note to editor or hide if none is selected
        grid
                .asSingleSelect()
                .addValueChangeListener { e: ComponentValueChangeEvent<Grid<Note?>, Note?> -> noteEditor.editNote(e.value) }
        // Instantiate and edit new Note the new button is clicked
        addNewNoteButton.addClickListener { noteEditor.editNote(Note(null, null, null)) }
        // Listen changes made by the editor, refresh data from backend
        noteEditor.setChangeHandler(object : NoteEditor.ChangeHandler {
            override fun onChange() {
                noteEditor.isVisible = false
                notesList(filter.value)
            }
        })
        // Initialize listing
        notesList("")
    }
}
package ru.notes.views.notes

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import ru.notes.model.Note
import ru.notes.views.component.NoteEditor
import ru.notes.views.main.MainView

@Route(value = "notes", layout = MainView::class)
@RouteAlias(value = "", layout = MainView::class)
@PageTitle("Notes")
@CssImport("./styles/views/notes/notes-view.css")

class NotesView(private val noteEditor: NoteEditor) : Div() {
    private val NEW_NOTE_BUTTON = "New note"
    private val grid: Grid<Note> = initGrid()
    private val filter = initFilter()
    private val addNewNoteButton = initNewNoteButton()
    private val toolbar = HorizontalLayout(filter, addNewNoteButton)
    private val wrapper = initGridLayout(grid)
    private val splitLayout = initSplitLayout(wrapper)

    init {
        setId("notes-view")
        refreshDataFromBackend()
        add(toolbar)
        add(splitLayout)
    }

    private fun initGrid(): Grid<Note> {
        val grid: Grid<Note> = Grid()
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        grid.addColumn { it.title }.setHeader("title")
        grid.addColumn { it.tag }.setHeader("tag")
        grid.addColumn { it.description }.setHeader("description")
        grid.setItems(noteEditor.getAll(""))
        grid.asSingleSelect() //edit new Note the new button or delete button is clicked
                .addValueChangeListener {
                    if (it.value != null)
                        noteEditor.editNote(it.value)
                    // if new or delete button is clicked it.value==null
                }
        return grid
    }

    private fun initFilter(): TextField {
        val filter = TextField()
        filter.placeholder = "filter"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener {
            grid.setItems(noteEditor.getAll(filter.value))
        }
        return filter
    }

    private fun initNewNoteButton(): Button {
        val addNewNoteButton = Button(NEW_NOTE_BUTTON, VaadinIcon.PLUS.create())
        addNewNoteButton.addClickListener {
            noteEditor.editNote(Note(null, null, null))
        }
        return addNewNoteButton
    }

    private fun refreshDataFromBackend() {
        noteEditor.setChangeHandler(object : NoteEditor.ChangeHandler {
            override fun onChange() {
                noteEditor.isVisible = false
                grid.setItems(noteEditor.getAll(filter.value))
            }
        })
    }

    private fun initGridLayout(grid: Grid<Note>): Div {
        val wrapper = Div()
        wrapper.setId("wrapper")
        wrapper.setWidthFull()
        wrapper.add(grid)
        return wrapper
    }

    private fun initSplitLayout(wrapper: Div): SplitLayout {
        val splitLayout = SplitLayout()
        splitLayout.setSizeFull()
        splitLayout.addToPrimary(wrapper)
        noteEditor.createEditorLayout(splitLayout)
        return splitLayout
    }
}
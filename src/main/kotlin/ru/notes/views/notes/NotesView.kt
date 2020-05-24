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
import ru.notes.repository.NoteRepository
import ru.notes.views.component.NoteEditor
import ru.notes.views.main.MainView

@Route(value = "notes", layout = MainView::class)
@RouteAlias(value = "", layout = MainView::class)
@PageTitle("Notes")
@CssImport("./styles/views/notes/notes-view.css")
//class NotesView: Div(), AfterNavigationObserver {
//    @Autowired
//    private lateinit var noteRepository: NoteRepository
//    private lateinit var noteEditor: NoteEditor
class NotesView(private val noteRepository: NoteRepository,
                private val noteEditor: NoteEditor
) : Div() {
    private val filter = TextField()
    private val addNewNoteButton = Button("New note", VaadinIcon.PLUS.create())
    private val toolbar = HorizontalLayout(filter, addNewNoteButton)
    private val grid: Grid<Note> = Grid()

    init {
        setId("notes-view")
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        grid.addColumn { it.description }.setHeader("description")
        grid.addColumn { it.tag }.setHeader("tag")
        grid.addColumn { it.title }.setHeader("title")
        initFilter()
        editNoteListener() //connect selected line to editor or hide if none is selected
        addNoteListener()  //edit new Note the new button is clicked
        refreshDataFromBackend()
        listNotes("")
        add(toolbar, grid, noteEditor)
        val splitLayout = SplitLayout()
        splitLayout.setSizeFull()
        createGridLayout(splitLayout)
        noteEditor.createEditorLayout(splitLayout)
        add(splitLayout)
    }

    private fun createGridLayout(splitLayout: SplitLayout) {
        val wrapper = Div()
        wrapper.setId("wrapper")
        wrapper.setWidthFull()
        splitLayout.addToPrimary(wrapper)
        wrapper.add(grid)
    }

    private fun initFilter() {
        filter.placeholder = "filter"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener {
            listNotes(it.value)
        }
    }

    private fun editNoteListener() {
        grid.asSingleSelect()
                .addValueChangeListener {
                    noteEditor.editNote(it.value)
                }
    }

    private fun addNoteListener() {
        addNewNoteButton.addClickListener {
            noteEditor.editNote(Note(null, null, null))
        }
    }

    private fun refreshDataFromBackend() {
        noteEditor.setChangeHandler(object : NoteEditor.ChangeHandler {
            override fun onChange() {
                noteEditor.isVisible = false
                listNotes(filter.value)
            }
        })
    }

    private fun listNotes(name: String) {
        grid.setItems(
                if (name.isEmpty())
                    noteRepository.findAll() else
                    noteRepository.findByName(name)
        )
    }

}
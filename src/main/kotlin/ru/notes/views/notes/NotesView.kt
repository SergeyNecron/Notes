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
import ru.notes.config.*
import ru.notes.dto.NoteDtoOut
import ru.notes.views.component.ChangeHandler
import ru.notes.views.component.NoteEditor
import ru.notes.views.main.MainView

@Route(value = "notes", layout = MainView::class)
@RouteAlias(value = "", layout = MainView::class)
@PageTitle("Notes")
@CssImport("./styles/views/notes/notes-view.css")
class NotesView(private val noteEditor: NoteEditor) : Div() {

    private val grid: Grid<NoteDtoOut> = initGrid()
    private val filter = initFilter()

    init {
        setId(Style.NOTES_CSS)
        val newNoteButton = initNewNoteButton()
        val toolbar = HorizontalLayout(filter, newNoteButton)
        val wrapper = initGridLayout(grid)
        val editor = noteEditor.initEditorLayout()
        val splitLayout = initSplitLayout(wrapper, editor)
        add(toolbar)
        add(splitLayout)
        setUpdateTable()
    }

    private fun initGrid(): Grid<NoteDtoOut> {
        val grid = Grid(NoteDtoOut::class.java)
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        grid.setColumns(TITLE_COLUMN, TAG_COLUMN, DESCRIPTION_COLUMN)
        grid.setHeightFull()
        grid.setItems(noteEditor.getAll(""))
        grid.asSingleSelect() //edit new Note the new button or delete button is clicked
                .addValueChangeListener {
                    if (it.value != null) // if new or delete button is clicked it.value==null
                        noteEditor.editNote(it.value)
                }
        return grid
    }

    private fun initFilter(): TextField {
        setId(Style.FILTER_CSS)
        val filter = TextField()
        filter.placeholder = ENTER_FILTER_NAME
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener {
            grid.setItems(noteEditor.getAll(filter.value))
        }
        return filter
    }

    private fun initNewNoteButton(): Button {
        val addNewNoteButton = Button(NEW_NOTE_BUTTON, VaadinIcon.PLUS.create())
        addNewNoteButton.addClickListener {
            noteEditor.editNote(NoteDtoOut())
        }
        return addNewNoteButton
    }

    private fun initGridLayout(grid: Grid<NoteDtoOut>): Div {
        val wrapper = Div()
        wrapper.setId(Style.WRAPPER_CSS)
        wrapper.setWidthFull()
        wrapper.add(grid)
        return wrapper
    }

    private fun initSplitLayout(table: Div, editor: Div): SplitLayout {
        val splitLayout = SplitLayout()
        splitLayout.setSizeFull()
        splitLayout.addToPrimary(table)
        splitLayout.addToSecondary(editor)
        return splitLayout
    }

    private fun setUpdateTable() {
        noteEditor.setChangeHandler(object : ChangeHandler {
            override fun updateTable() {
                grid.setItems(noteEditor.getAll(filter.value))
                noteEditor.editorDiv.isVisible = false
            }
        })
    }
}
package ru.notes.views.note

import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.notes.config.Style
import ru.notes.views.component.ChangeHandler
import ru.notes.views.component.NoteEditor
import ru.notes.views.main.MainView

@Route(value = "note", layout = MainView::class)
@PageTitle("Note")
@CssImport("./styles/views/note/note-view.css")
class NoteView(private val noteEditor: NoteEditor) : Div() {

    init {
        setId(Style.NOTE_CSS)
        val editor = noteEditor.initEditorLayout()
        settingNoteEditor()
        add(editor)
        setUpdateTable()
    }

    private fun settingNoteEditor() {
        noteEditor.editorDiv.isVisible = true
        noteEditor.editorDiv.setWidthFull()
        noteEditor.delete.isVisible = false
        noteEditor.cancel.isVisible = false
    }

    private fun setUpdateTable() {
        noteEditor.setChangeHandler(object : ChangeHandler {
            override fun updateTable() {
            }
        })
    }
}
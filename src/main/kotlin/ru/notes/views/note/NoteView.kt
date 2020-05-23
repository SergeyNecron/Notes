package ru.notes.views.note

import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.notes.views.main.MainView

@Route(value = "note", layout = MainView::class)
@PageTitle("Note")
@CssImport("./styles/views/note/note-view.css")
class NoteView : Div() {
    fun NoteView() {
        setId("note-ru.notes.view")
        add(Label("Content placeholder"))
    }
}
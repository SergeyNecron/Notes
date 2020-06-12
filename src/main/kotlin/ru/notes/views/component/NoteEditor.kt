package ru.notes.views.component

import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.dto.NoteDtoIn
import ru.notes.dto.NoteDtoOut
import ru.notes.service.NoteService

@SpringComponent
@UIScope
class NoteEditor @Autowired constructor(
        private val service: NoteService
) : Editor() {
    /* Fields to edit properties in NoteDtoOut */
    private val title = TextField("", "title")
    private val tag = TextField("", "tag")
    private val description = TextField("", "description")

    private val binder = Binder(NoteDtoOut::class.java)
    private var noteDto = NoteDtoOut()
    private lateinit var changeHandler: ChangeHandler
    override lateinit var editorDiv: Div

    init {
        binder.bindInstanceFields(this) // bind using naming convention
    }

    fun setChangeHandler(changeHandler: ChangeHandler) {
        this.changeHandler = changeHandler
    }

    override fun save() {
        if (noteDto.id == 0L)
            service.addNote(NoteDtoIn(noteDto))
        else service.updateNote(noteDto.id, noteDto)
        changeHandler.onChange()
    }

    override fun delete() {
        if (service.deleteNote(noteDto.id)) {
            changeHandler.onChange()
        }
    }

    fun editNote(dto: NoteDtoOut) {
        this.noteDto = dto
        binder.bean = this.noteDto
//        binder.readBean(note)
        editorDiv.isVisible = true
        title.focus()
    }

    fun createEditorLayout(splitLayout: SplitLayout) {
        editorDiv = Div()
        editorDiv.setId("editor-layout")
        editorDiv.isVisible = false
        val formLayout = FormLayout()
        addFormItem(editorDiv, formLayout, title, "title")
        addFormItem(editorDiv, formLayout, tag, "tag")
        addFormItem(editorDiv, formLayout, description, "description")
        createButtonLayout(editorDiv)
        splitLayout.addToSecondary(editorDiv)
    }

    fun getAll(name: String): List<NoteDtoOut>? =
            service.findNotes(name)
}
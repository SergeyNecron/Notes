package ru.notes.views.component

import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.config.*
import ru.notes.dto.NoteDtoIn
import ru.notes.dto.NoteDtoOut
import ru.notes.service.NoteService

@SpringComponent
@UIScope
class NoteEditor @Autowired constructor(
        private val service: NoteService
) : Editor() {
    /* Fields to edit properties in NoteDtoOut */
    val title = TextField("", ENTER_TITLE)
    val tag = TextField("", ENTER_TAG)
    val description = TextArea("", ENTER_DESCRIPTION)

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
        changeHandler.updateTable()
    }

    override fun delete() {
        if (service.deleteNote(noteDto.id)) {
            changeHandler.updateTable()
        }
    }

    fun editNote(dto: NoteDtoOut) {
        delete.isVisible = true
        cancel.isVisible = true
        this.noteDto = dto
        binder.bean = this.noteDto
        editorDiv.isVisible = true
        title.focus()
    }

    fun createEditorLayout() {
        editorDiv = Div()
        editorDiv.setId(Style.EDITOR_CSS)
        editorDiv.isVisible = false
    }

    fun formLayoutSetNote() {
        val formLayout = FormLayout()
        formLayout.setWidthFull()
        description.setWidthFull()
        description.height = DESCRIPTION_HEIGHT
        addFormItem(editorDiv, formLayout, title, TITLE)
        addFormItem(editorDiv, formLayout, tag, TAG)
        val descriptionFormItem = addFormItem(editorDiv, formLayout,
                description, DESCRIPTION)
        formLayout.setColspan(descriptionFormItem, 2)
    }

    fun initEditorLayout(): Div {
        createEditorLayout()
        formLayoutSetNote()
        editorDiv.add(createButtonLayout())
        return editorDiv
    }

    fun getAll(name: String): List<NoteDtoOut>? =
            service.findNotes(name)
}
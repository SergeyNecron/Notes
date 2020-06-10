package ru.notes.views.component

import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.dto.NoteDtoOut
import ru.notes.service.NoteService

@SpringComponent
@UIScope
class NoteEditor @Autowired constructor(
        private val service: NoteService
) : VerticalLayout(), KeyNotifier {
    private val title = TextField("", "title")
    private val tag = TextField("", "tag")
    private val description = TextField("", "description")
    private val save = Button("Save")
    private val cancel = Button("Cancel")
    private val delete = Button("Delete")

    private val binder = Binder(NoteDtoOut::class.java)
    private var noteDto = NoteDtoOut()

    private lateinit var changeHandler: ChangeHandler
    var editorDiv = Div()

    init {
        binder.bindInstanceFields(this) // bind using naming convention
        configureAndStyleComponents()
        wireActionButtonsToSaveDeleteAndReset()
    }

    private fun configureAndStyleComponents() {
        isSpacing = true
        save.element.themeList.add("primary")
        delete.element.themeList.add("error")
        addKeyPressListener(Key.ENTER, ComponentEventListener { save() })
    }

    private fun wireActionButtonsToSaveDeleteAndReset() {
        save.addClickListener { save() }
        delete.addClickListener { delete() }
        cancel.addClickListener { editorDiv.isVisible = false }
    }

    private fun save() {
        if (noteDto.id == 0L)
            service.addNote(noteDto)
        else service.updateNote(noteDto.id, noteDto)
        changeHandler.onChange()
        editorDiv.isVisible = false
    }

    private fun delete() {
        service.deleteNote(noteDto.id)
        changeHandler.onChange()
        editorDiv.isVisible = false
    }

    fun editNote(dto: NoteDtoOut) {
        this.noteDto = dto
        binder.bean = this.noteDto
//        binder.readBean(note)
        editorDiv.isVisible = true
        title.focus()
    }

    fun setChangeHandler(changeHandler: ChangeHandler) {
        this.changeHandler = changeHandler
    }

    interface ChangeHandler {
        fun onChange()
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

    private fun createButtonLayout(editorDiv: Div) {
        val buttonLayout = HorizontalLayout()
        buttonLayout.setId("button-layout")
        buttonLayout.setWidthFull()
        buttonLayout.isSpacing = true
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        buttonLayout.add(cancel, save, delete)
        editorDiv.add(buttonLayout)
    }

    private fun addFormItem(wrapper: Div, formLayout: FormLayout,
                            field: TextField, fieldName: String) {
        formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add("full-width")
    }

    fun getAll(name: String): List<NoteDtoOut>? =
            service.findNotes(name)
}
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
import ru.notes.model.Note
import ru.notes.repository.NoteRepository


@SpringComponent
@UIScope
class NoteEditor : VerticalLayout(), KeyNotifier {
    @Autowired
    private lateinit var noteRepository: NoteRepository
    private val title = TextField("", "title")
    private val tag = TextField("", "tag")
    private val description = TextField("", "description")
    private val save = Button("Save")
    private val cancel = Button("Cancel")
    private val delete = Button("Delete")
    private val binder = Binder(Note::class.java)
    private var note: Note = Note(null, null, null)
    private lateinit var changeHandler: ChangeHandler
    val editorDiv = Div()
    init {
        binder.bindInstanceFields(this) // bind using naming convention
        configureAndStyleComponents()
        wireActionButtonsToSaveDeleteAndReset()
        add(title, tag, description)
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
        noteRepository.save(note)
        changeHandler.onChange()
        editorDiv.isVisible = false
    }

    private fun delete() {
        noteRepository.delete(note)
        changeHandler.onChange()
        editorDiv.isVisible = false
    }

    fun editNote(note: Note?) {
        if (note == null) {
            editorDiv.isVisible = true
            return
        }
        this.note = noteRepository.findById(note.id).orElse(note)
        binder.bean = this.note
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
        editorDiv.setId("editor-layout")
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

}
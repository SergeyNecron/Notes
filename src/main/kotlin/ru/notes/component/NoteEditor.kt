package ru.notes.component

import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
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
    private val buttons = HorizontalLayout(save, cancel, delete)
    private val binder = Binder(Note::class.java)
    private var note: Note = Note(null, null, null)
    private lateinit var changeHandler: ChangeHandler

    init {
        binder.bindInstanceFields(this) // bind using naming convention
        configureAndStyleComponents()
        wireActionButtonsToSaveDeleteAndReset()
        isVisible = false
        add(title, tag, description, buttons)
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
        cancel.addClickListener { isVisible = false }
    }

    private fun save() {
        noteRepository.save(note)
        changeHandler.onChange()
    }

    private fun delete() {
        noteRepository.delete(note)
        changeHandler.onChange()
    }

    fun editNote(note: Note?) {
        if (note == null) {
            isVisible = false
            return
        }
        this.note = noteRepository.findById(note.id).orElse(note)
        binder.bean = this.note
//        binder.readBean(note)
//        note.id = null
        isVisible = true
        title.focus()
    }

    fun setChangeHandler(changeHandler: ChangeHandler) {
        this.changeHandler = changeHandler
    }

    interface ChangeHandler {
        fun onChange()
    }


}
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
import com.vaadin.flow.component.textfield.TextField

abstract class Editor : VerticalLayout(), KeyNotifier {
    /* Action buttons */
    private val save = Button("Save")
    private val cancel = Button("Cancel")
    private val delete = Button("Delete")

    abstract var editorDiv: Div

    init {
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

    protected fun createButtonLayout(editorDiv: Div) {
        val buttonLayout = HorizontalLayout()
        buttonLayout.setId("button-layout")
        buttonLayout.setWidthFull()
        buttonLayout.isSpacing = true
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        buttonLayout.add(cancel, save, delete)
        editorDiv.add(buttonLayout)
    }

    protected fun addFormItem(wrapper: Div, formLayout: FormLayout,
                              field: TextField, fieldName: String) {
        formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add("full-width")
    }

    abstract fun save()
    abstract fun delete()

}
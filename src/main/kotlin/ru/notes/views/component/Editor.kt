package ru.notes.views.component

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.ComponentEventListener
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.KeyNotifier
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.formlayout.FormLayout.FormItem
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import ru.notes.config.CANCEL
import ru.notes.config.DELETE
import ru.notes.config.SAVE
import ru.notes.config.Style

abstract class Editor : VerticalLayout(), KeyNotifier {

    private val save = Button(SAVE)
    val cancel = Button(CANCEL)
    val delete = Button(DELETE)

    abstract var editorDiv: Div

    init {
        configureAndStyleComponents()
        wireActionButtonsToSaveDeleteAndReset()
    }

    private fun configureAndStyleComponents() {
        isSpacing = true
        save.element.themeList.add(Style.FIELD_PRIMARY_CSS)
        delete.element.themeList.add(Style.FIELD_ERROR_CSS)
        addKeyPressListener(Key.ENTER, ComponentEventListener { save() })
    }

    private fun wireActionButtonsToSaveDeleteAndReset() {
        save.addClickListener { save() }
        delete.addClickListener { delete() }
        cancel.addClickListener { editorDiv.isVisible = false }
    }

    fun createButtonLayout(): HorizontalLayout {
        val buttonLayout = HorizontalLayout()
        buttonLayout.setId(Style.BUTTON_CSS)
        buttonLayout.setWidthFull()
        buttonLayout.isSpacing = true
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        buttonLayout.add(cancel, save, delete)
        return buttonLayout
    }

    protected fun addFormItem(wrapper: Div, formLayout: FormLayout,
                              field: Component, fieldName: String): FormItem {
        val formItem = formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add(Style.FIELD_WIDTH_CSS)
        return formItem
    }

    abstract fun save()
    abstract fun delete()

}
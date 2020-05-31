package ru.notes.views.user

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.formlayout.FormLayout.FormItem
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.notes.backend.Employee
import ru.notes.views.main.MainView

@Route(value = "registration", layout = MainView::class)
@PageTitle("User")
@CssImport("./styles/views/user/user-view.css")
class UserView : Div() {
    private val firstname = TextField()
    private val lastname = TextField()
    private val patronymic = TextField()
    private val email = TextField()
    private val notes = TextArea()
    private val cancel = Button("Cancel")
    private val save = Button("Save")

    init {
        setId("user-view")
        val wrapper = createWrapper()
        createTitle(wrapper)
        createFormLayout(wrapper)
        createButtonLayout(wrapper)

        // Configure Form
        val binder = Binder(Employee::class.java)

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this)
        cancel.addClickListener { binder.readBean(null) }
        save.addClickListener { Notification.show("Not implemented") }
        add(wrapper)
    }

    private fun createTitle(wrapper: VerticalLayout) {
        val h1 = H1("Form")
        wrapper.add(h1)
    }

    private fun createWrapper(): VerticalLayout {
        val wrapper = VerticalLayout()
        wrapper.setId("wrapper")
        wrapper.isSpacing = false
        return wrapper
    }

    private fun createFormLayout(wrapper: VerticalLayout) {
        val formLayout = FormLayout()
        addFormItem(wrapper, formLayout, firstname, "First name")
        addFormItem(wrapper, formLayout, lastname, "Last name")
        addFormItem(wrapper, formLayout, patronymic, "Patronymic")
        val emailFormItem = addFormItem(wrapper, formLayout,
                email, "Email")
        formLayout.setColspan(emailFormItem, 2)
        val notesFormItem = addFormItem(wrapper, formLayout,
                notes, "Notes")
        formLayout.setColspan(notesFormItem, 2)
    }

    private fun createButtonLayout(wrapper: VerticalLayout) {
        val buttonLayout = HorizontalLayout()
        buttonLayout.addClassName("button-layout")
        buttonLayout.setWidthFull()
        buttonLayout.justifyContentMode = FlexComponent.JustifyContentMode.END
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        buttonLayout.add(cancel)
        buttonLayout.add(save)
        wrapper.add(buttonLayout)
    }

    private fun addFormItem(wrapper: VerticalLayout,
                            formLayout: FormLayout, field: Component, fieldName: String): FormItem {
        val formItem = formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add("full-width")
        return formItem
    }


}
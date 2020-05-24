package ru.notes.views.users

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.backend.BackendService
import ru.notes.backend.Employee
import ru.notes.views.main.MainView

@Route(value = "users", layout = MainView::class)
@PageTitle("Users")
@CssImport("./styles/views/users/users-view.css")
class UsersView : Div(), AfterNavigationObserver {
    @Autowired
    private lateinit var service: BackendService
    private val employees: Grid<Employee>
    private val firstname = TextField()
    private val lastname = TextField()
    private val email = TextField()
    private val password = PasswordField()
    private val cancel = Button("Cancel")
    private val save = Button("Save")
    private val binder: Binder<Employee>

    init {
        setId("users-view")
        // Configure Grid
        employees = Grid()
        employees.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        employees.setHeightFull()
        employees.addColumn { it.firstname }.setHeader("First name")
        employees.addColumn { it.lastname }.setHeader("Last name")
        employees.addColumn { it.email }.setHeader("Email")

        //when a row is selected or deselected, populate form
        employees.asSingleSelect().addValueChangeListener { populateForm(it.value) }

        // Configure Form
        binder = Binder(Employee::class.java)

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this)
        // note that password field isn't bound since that property doesn't exist in
        // Employee

        // the grid valueChangeEvent will clear the form too
        cancel.addClickListener { employees.asSingleSelect().clear() }
        save.addClickListener { Notification.show("Not implemented") }
        val splitLayout = SplitLayout()
        splitLayout.setSizeFull()
        createGridLayout(splitLayout)
        createEditorLayout(splitLayout)
        add(splitLayout)
    }

    private fun createEditorLayout(splitLayout: SplitLayout) {
        val editorDiv = Div()
        editorDiv.setId("editor-layout")
        val formLayout = FormLayout()
        addFormItem(editorDiv, formLayout, firstname, "First name")
        addFormItem(editorDiv, formLayout, lastname, "Last name")
        addFormItem(editorDiv, formLayout, email, "Email")
        addFormItem(editorDiv, formLayout, password, "Password")
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
        buttonLayout.add(cancel, save)
        editorDiv.add(buttonLayout)
    }

    private fun createGridLayout(splitLayout: SplitLayout) {
        val wrapper = Div()
        wrapper.setId("wrapper")
        wrapper.setWidthFull()
        splitLayout.addToPrimary(wrapper)
        wrapper.add(employees)
    }

    private fun addFormItem(wrapper: Div, formLayout: FormLayout,
                            field: TextField, fieldName: String) {
        formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add("full-width")
    }

    private fun addFormItem(wrapper: Div, formLayout: FormLayout,
                            field: PasswordField, fieldName: String) {
        formLayout.addFormItem(field, fieldName)
        wrapper.add(formLayout)
        field.element.classList.add("full-width")
    }

    override fun afterNavigation(event: AfterNavigationEvent) {

        // Lazy init of the grid items, happens only when we are sure the ru.notes.view will be
        // shown to the user
        employees.setItems(service.employees)
    }

    private fun populateForm(value: Employee) {
        // Value can be null as well, that clears the form
        binder.readBean(value)

        // The password field isn't bound through the binder, so handle that
        password.value = ""
    }


}
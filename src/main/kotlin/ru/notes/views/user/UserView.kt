package ru.notes.views.user

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.formlayout.FormLayout.FormItem
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.config.*
import ru.notes.dto.UserDtoIn
import ru.notes.service.UserService
import ru.notes.views.main.MainView

@Route(value = "registration", layout = MainView::class)
@PageTitle("User")
@CssImport("./styles/views/user/user-view.css")
class UserView @Autowired constructor(
        private val service: UserService
) : Div() {
    private var userDtoIn = UserDtoIn()

    /* Fields to edit properties in UserDtoOut */
    private val firstName = TextField("", ENTER_FIRST_NAME)
    private val lastName = TextField("", ENTER_LAST_NAME)
    private val patronymic = TextField("", ENTER_PATRONYMIC)

    //    private val login = TextField("", ENTER_LOGIN)
    private val password = PasswordField("", ENTER_PASSWORD)
    private val repeatPassword = PasswordField("", ENTER_REPEAT_PASSWORD)
    private val email = TextField("", ENTER_EMAIL)

    /* Action buttons */
    private val save = Button(REGISTRATION)

    private val binder = Binder(UserDtoIn::class.java)

    init {
        setId(Style.USER_CSS)
        binder.bindInstanceFields(this)
        binder.bean = this.userDtoIn
        val wrapper = createWrapper()
        val formLayout = createFormLayout()
        val buttonLayout = createButtonLayout()
        wrapper.add(formLayout)
        wrapper.add(buttonLayout)
        add(wrapper)
    }

    private fun createWrapper(): VerticalLayout {
        val wrapper = VerticalLayout()
        wrapper.setId(Style.WRAPPER_CSS)
        return wrapper
    }

    private fun createFormLayout(): FormLayout {
        val formLayout = FormLayout()
        addFormItem(formLayout, firstName, FIRST_NAME)
        addFormItem(formLayout, lastName, LAST_NAME)
        addFormItem(formLayout, patronymic, PATRONYMIC)
//        addFormItem(formLayout, login, LOGIN)
        addFormItem(formLayout, password, PASSWORD)
        addFormItem(formLayout, repeatPassword, REPEAT_PASSWORD)
        addFormItem(formLayout, email, EMAIL)
        return formLayout
    }

    private fun addFormItem(formLayout: FormLayout, field: Component, fieldName: String
    ): FormItem {
        val formItem = formLayout.addFormItem(field, fieldName)
        field.element.classList.add(Style.FIELD_WIDTH_CSS)
        return formItem
    }

    private fun createButtonLayout(): HorizontalLayout {
        val buttonLayout = HorizontalLayout()
        buttonLayout.addClassName(Style.BUTTON_CSS)
        buttonLayout.setWidthFull()
        buttonLayout.justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        save.addClickListener { save() }
        buttonLayout.add(save)
        return buttonLayout
    }

    private fun save() {
        service.add(userDtoIn)
        this.userDtoIn = UserDtoIn()
        binder.bean = this.userDtoIn
    }

}
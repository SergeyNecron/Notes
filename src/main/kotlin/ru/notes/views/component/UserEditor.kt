package ru.notes.views.component

import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.dto.UserDtoIn
import ru.notes.dto.UserDtoOut
import ru.notes.service.UserService

@SpringComponent
@UIScope
class UserEditor @Autowired constructor(
        private val service: UserService
) : Editor() {
    /* Fields to edit properties in UserDtoOut */
    private val firstname = TextField("", "firstname")
    private val lastname = TextField("", "lastname")
    private val patronymic = TextField("", "patronymic")
    private val email = TextField("", " email")
    private val password = TextField("", "password")

    private val binder = Binder(UserDtoOut::class.java)
    private var userDto = UserDtoOut()
    private lateinit var changeHandler: ChangeHandler
    override lateinit var editorDiv: Div

    init {
        binder.bindInstanceFields(this) // bind using naming convention
    }

    fun setChangeHandler(changeHandler: ChangeHandler) {
        this.changeHandler = changeHandler
    }

    override fun save() {
        if (userDto.id == 0L)
            service.add(UserDtoIn(userDto))
        else service.update(userDto.id, userDto)
        changeHandler.onChange()
    }

    override fun delete() {
        service.delete(userDto.id)
        changeHandler.onChange()
    }

    fun editUser(dto: UserDtoOut) {
        this.userDto = dto
        binder.bean = this.userDto
//        binder.readBean(note)
        editorDiv.isVisible = true
        firstname.focus()
    }

    fun createEditorLayout(splitLayout: SplitLayout) {
        editorDiv = Div()
        editorDiv.setId("editor-layout")
        editorDiv.isVisible = false
        val formLayout = FormLayout()
        addFormItem(editorDiv, formLayout, firstname, "firstname")
        addFormItem(editorDiv, formLayout, lastname, "lastname")
        addFormItem(editorDiv, formLayout, patronymic, "patronymic")
        addFormItem(editorDiv, formLayout, email, "email")
        addFormItem(editorDiv, formLayout, password, "password")
        createButtonLayout(editorDiv)
        splitLayout.addToSecondary(editorDiv)
    }

    fun getAll(name: String): List<UserDtoOut>? =
            service.findUsers(name)
}
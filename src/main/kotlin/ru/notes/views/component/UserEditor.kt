package ru.notes.views.component

import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ru.notes.config.*
import ru.notes.dto.UserDtoIn
import ru.notes.dto.UserDtoOut
import ru.notes.service.UserService

@SpringComponent
@UIScope
class UserEditor @Autowired constructor(
        private val service: UserService
) : Editor() {
    /* Fields to edit properties in UserDtoOut */
    private val firstName = TextField("", ENTER_FIRST_NAME)
    private val lastName = TextField("", ENTER_LAST_NAME)
    private val patronymic = TextField("", ENTER_PATRONYMIC)

    //    private val login = TextField("", ENTER_LOGIN)
    private val email = TextField("", ENTER_EMAIL)
//    private var enabled: Boolean

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
        changeHandler.updateTable()
    }

    override fun delete() {
        service.delete(userDto.id)
        changeHandler.updateTable()
    }

    fun editUser(dto: UserDtoOut) {
        this.userDto = dto
        binder.bean = this.userDto
        editorDiv.isVisible = true
        firstName.focus()
    }

    fun createEditorLayout() {
        editorDiv = Div()
        editorDiv.setId(Style.EDITOR_CSS)
        editorDiv.isVisible = false

    }

    fun formLayoutSetNote() {
        val formLayout = FormLayout()
        addFormItem(editorDiv, formLayout, firstName, FIRST_NAME)
        addFormItem(editorDiv, formLayout, lastName, LAST_NAME)
        addFormItem(editorDiv, formLayout, patronymic, PATRONYMIC)
        addFormItem(editorDiv, formLayout, email, EMAIL)
    }

    fun initEditorLayout(): Div {
        createEditorLayout()
        formLayoutSetNote()
        editorDiv.add(createButtonLayout())
        return editorDiv
    }

    fun getAll(name: String): List<UserDtoOut>? =
            service.findUsers(name)
}
package ru.notes.views.users

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.splitlayout.SplitLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.notes.config.*
import ru.notes.dto.UserDtoOut
import ru.notes.views.component.ChangeHandler
import ru.notes.views.component.UserEditor
import ru.notes.views.main.MainView

@Route(value = "users", layout = MainView::class)
@PageTitle("Users")
@CssImport("./styles/views/users/users-view.css")
class UsersView(private val userEditor: UserEditor) : Div() {

    private val grid: Grid<UserDtoOut> = initGrid()
    private val filter = initFilter()

    init {
        setId(Style.USERS_CSS)
        val addNewUserButton = initNewUserButton()
        val toolbar = HorizontalLayout(filter, addNewUserButton)
        val wrapper = initGridLayout(grid)
        val editor = userEditor.initEditorLayout()
        val splitLayout = initSplitLayout(wrapper, editor)
        add(toolbar)
        add(splitLayout)
        setUpdateTable()
    }

    private fun initGrid(): Grid<UserDtoOut> {
        val grid = Grid(UserDtoOut::class.java)
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        grid.setColumns(FIRST_NAME_COLUMN, LAST_NAME_COLUMN, PATRONYMIC_COLUMN, EMAIL_COLUMN)
        grid.setHeightFull()
        grid.setItems(userEditor.getAll(""))
        grid.asSingleSelect() //edit new User the new button or delete button is clicked
                .addValueChangeListener {
                    if (it.value != null)
                        userEditor.editUser(it.value)
                    // if new or delete button is clicked it.value==null
                }
        return grid
    }

    private fun initFilter(): TextField {
        setId(Style.FILTER_CSS)
        val filter = TextField()
        filter.placeholder = ENTER_FILTER_NAME
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener {
            grid.setItems(userEditor.getAll(filter.value))
        }
        return filter
    }

    private fun initNewUserButton(): Button {
        val addNewUserButton = Button(NEW_USER_BUTTON, VaadinIcon.PLUS.create())
        addNewUserButton.addClickListener {
            userEditor.editUser(UserDtoOut())
        }
        return addNewUserButton
    }

    private fun initGridLayout(grid: Grid<UserDtoOut>): Div {
        val wrapper = Div()
        wrapper.setId(Style.WRAPPER_CSS)
        wrapper.setWidthFull()
        wrapper.add(grid)
        return wrapper
    }

    private fun initSplitLayout(wrapper: Div, editor: Div): SplitLayout {
        val splitLayout = SplitLayout()
        splitLayout.setSizeFull()
        splitLayout.addToPrimary(wrapper)
        splitLayout.addToSecondary(editor)
        return splitLayout
    }

    private fun setUpdateTable() {
        userEditor.setChangeHandler(object : ChangeHandler {
            override fun updateTable() {
                grid.setItems(userEditor.getAll(filter.value))
                userEditor.editorDiv.isVisible = false
            }
        })
    }
}
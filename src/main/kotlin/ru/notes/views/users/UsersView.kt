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

    companion object {
        const val NEW_NOTE_BUTTON = "New user"
    }

    init {
        val addNewUserButton = initNewUserButton()
        val toolbar = HorizontalLayout(filter, addNewUserButton)
        val wrapper = initGridLayout(grid)
        val splitLayout = initSplitLayout(wrapper)
        setId("users-view")
        add(toolbar)
        add(splitLayout)
        refreshDataFromBackend()
    }

    private fun initGrid(): Grid<UserDtoOut> {
        val grid = Grid(UserDtoOut::class.java)
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        grid.setColumns("firstname", "lastname", "patronymic", "email")
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
        val filter = TextField()
        filter.placeholder = "filter"
        filter.valueChangeMode = ValueChangeMode.EAGER
        filter.addValueChangeListener {
            grid.setItems(userEditor.getAll(filter.value))
        }
        return filter
    }

    private fun initNewUserButton(): Button {
        val addNewUserButton = Button(NEW_NOTE_BUTTON, VaadinIcon.PLUS.create())
        addNewUserButton.addClickListener {
            userEditor.editUser(UserDtoOut())
        }
        return addNewUserButton
    }

    private fun initGridLayout(grid: Grid<UserDtoOut>): Div {
        val wrapper = Div()
        wrapper.setId("wrapper")
        wrapper.setWidthFull()
        wrapper.add(grid)
        return wrapper
    }

    private fun initSplitLayout(wrapper: Div): SplitLayout {
        val splitLayout = SplitLayout()
        splitLayout.setSizeFull()
        splitLayout.addToPrimary(wrapper)
        userEditor.createEditorLayout(splitLayout)
        return splitLayout
    }

    private fun refreshDataFromBackend() {
        userEditor.setChangeHandler(object : ChangeHandler {
            override fun onChange() {
                grid.setItems(userEditor.getAll(filter.value))
            }
        })
    }
}
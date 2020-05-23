package ru.notes.views.main

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.tabs.TabsVariant
import com.vaadin.flow.router.RouteConfiguration
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.server.PWA
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import ru.notes.views.dashboard.DashboardView
import ru.notes.views.note.NoteView
import ru.notes.views.notes.NotesView
import ru.notes.views.user.UserView
import ru.notes.views.users.UsersView
import java.util.*

/**
 * The main ru.notes.view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "fine-notes", shortName = "fine-notes")
@Theme(value = Lumo::class, variant = Lumo.LIGHT)
class MainView : AppLayout() {
    private val menu: Tabs
    override fun afterNavigation() {
        super.afterNavigation()
        selectTab()
    }

    private fun selectTab() {
        val target = RouteConfiguration.forSessionScope().getUrl(content.javaClass)
        val tabToSelect = menu.children.filter { tab: Component ->
            val child = tab.children.findFirst().get()
            child is RouterLink && child.href == target
        }.findFirst()
        tabToSelect.ifPresent { tab: Component? -> menu.selectedTab = tab as Tab? }
    }

    companion object {
        private fun createMenuTabs(): Tabs {
            val tabs = Tabs()
            tabs.orientation = Tabs.Orientation.VERTICAL
            tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL)
            tabs.setId("tabs")
            tabs.add(*availableTabs)
            return tabs
        }

        private val availableTabs: Array<Tab>
            private get() {
                val tabs: MutableList<Tab> = ArrayList()
                tabs.add(createTab("Notes", NotesView::class.java))
                tabs.add(createTab("Note", NoteView::class.java))
                tabs.add(createTab("Users", UsersView::class.java))
                tabs.add(createTab("User", UserView::class.java))
                tabs.add(createTab("Dashboard", DashboardView::class.java))
                return tabs.toTypedArray()
            }

        private fun createTab(title: String, viewClass: Class<out Component>): Tab {
            return createTab(populateLink(RouterLink(null, viewClass), title))
        }

        private fun createTab(content: Component): Tab {
            val tab = Tab()
            tab.add(content)
            return tab
        }

        private fun <T : HasComponents?> populateLink(a: T, title: String): T {
            a!!.add(title)
            return a
        }
    }

    init {
        primarySection = Section.DRAWER
        addToNavbar(true, DrawerToggle())
        menu = createMenuTabs()
        addToDrawer(menu)
    }
}
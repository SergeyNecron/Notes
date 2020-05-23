package ru.notes.views.dashboard

import com.vaadin.flow.component.board.Board
import com.vaadin.flow.component.charts.Chart
import com.vaadin.flow.component.charts.model.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.router.AfterNavigationEvent
import com.vaadin.flow.router.AfterNavigationObserver
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import ru.notes.views.main.MainView
import java.time.LocalDate
import java.util.*

@Route(value = "dashboard", layout = MainView::class)
@PageTitle("Dashboard")
@CssImport(value = "./styles/views/dashboard/dashboard-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
class DashboardView : Div(), AfterNavigationObserver {
    private val usersH2 = H2()
    private val eventsH2 = H2()
    private val conversionH2 = H2()
    private val grid = Grid<HealthGridItem>()
    private val monthlyVisitors = Chart()
    private val responseTimes = Chart()
    private fun createBadge(title: String, h2: H2, h2ClassName: String,
                            description: String, badgeTheme: String): WrapperCard {
        val titleSpan = Span(title)
        titleSpan.element.setAttribute("theme", badgeTheme)
        h2.addClassName(h2ClassName)
        val descriptionSpan = Span(description)
        descriptionSpan.addClassName("secondary-text")
        return WrapperCard("wrapper", arrayOf(titleSpan, h2, descriptionSpan), "card",
                "space-m")
    }

    override fun afterNavigation(event: AfterNavigationEvent) {

        // Set some data when this ru.notes.view is displayed.

        // Top row widgets
        usersH2.text = "745"
        eventsH2.text = "54.6k"
        conversionH2.text = "18%"

        // First chart
        var configuration = monthlyVisitors.configuration
        configuration.addSeries(ListSeries("Tokyo", 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4,
                194.1, 95.6, 54.4))
        configuration.addSeries(
                ListSeries("New York", 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3))
        configuration.addSeries(
                ListSeries("London", 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2))
        configuration.addSeries(
                ListSeries("Berlin", 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1))
        var x = XAxis()
        x.crosshair = Crosshair()
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        configuration.addxAxis(x)
        var y = YAxis()
        y.min = 0
        configuration.addyAxis(y)

        // Grid
        val gridItems: MutableList<HealthGridItem> = ArrayList()
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "M\u00FCnster", "Germany", "Good", "badge"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Cluj-Napoca", "Romania", "Good", "badge"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Ciudad Victoria", "Mexico", "Good", "badge"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Ebetsu", "Japan", "Excellent", "badge success"))
        gridItems
                .add(HealthGridItem(LocalDate.of(2019, 1, 14), "S\u00E3o Bernardo do Campo", "Brazil", "Good", "badge"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Maputo", "Mozambique", "Good", "badge"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Warsaw", "Poland", "Good", "badge"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Kasugai", "Japan", "Failing", "badge error"))
        gridItems.add(HealthGridItem(LocalDate.of(2019, 1, 14), "Lancaster", "United States", "Excellent",
                "badge success"))
        grid.setItems(gridItems)

        // Second chart
        configuration = responseTimes.configuration
        configuration
                .addSeries(ListSeries("Tokyo", 7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6))
        configuration
                .addSeries(ListSeries("London", 3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))
        x = XAxis()
        x.crosshair = Crosshair()
        x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        configuration.addxAxis(x)
        y = YAxis()
        y.min = 0
        configuration.addyAxis(y)
    }

    init {
        setId("dashboard-view")
        val board = Board()
        board.addRow(
                createBadge("Users", usersH2, "primary-text", "Current users in the app", "badge"),
                createBadge("Events", eventsH2, "success-text", "Events from the views", "badge success"),
                createBadge("Conversion", conversionH2, "error-text", "User conversion rate", "badge error")
        )
        monthlyVisitors.configuration
                .setTitle("Monthly visitors per city")
        monthlyVisitors.configuration.chart.type = ChartType.COLUMN
        val monthlyVisitorsWrapper = WrapperCard("wrapper", arrayOf(monthlyVisitors), "card")
        board.add(monthlyVisitorsWrapper)
//        grid.addColumn { obj: HealthGridItem -> obj.getCity() }.setHeader("City")
//        grid.addColumn(ComponentRenderer(SerializableFunction<HealthGridItem, Span> { item: HealthGridItem ->
//            val span = Span(item.getStatus())
//            span.element.themeList.add(item.getTheme())
//            span
//        })).setFlexGrow(0).setWidth("100px").setHeader("Status")
//        grid.addColumn { obj: HealthGridItem -> obj.getItemDate() }.setHeader("Date").width = "140px"
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER)
        val gridWrapper = WrapperCard("wrapper", arrayOf(H3("Service health"), grid), "card")
        responseTimes.configuration.setTitle("Response times")
        val responseTimesWrapper = WrapperCard("wrapper", arrayOf(responseTimes), "card")
        board.addRow(gridWrapper, responseTimesWrapper)
        add(board)
    }
}
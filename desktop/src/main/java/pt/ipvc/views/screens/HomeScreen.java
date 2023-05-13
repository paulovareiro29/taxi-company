package pt.ipvc.views.screens;

import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.layout.items.BookingStatistics;
import pt.ipvc.layout.items.ProfitStatistics;

public class HomeScreen extends Screen {

    private final ProfitStatistics profitStatistics;
    private final BookingStatistics bookingStatistics;

    public HomeScreen() {
        Heading title = new Heading("Home page");

        profitStatistics = new ProfitStatistics();
        bookingStatistics = new BookingStatistics();

        this.getChildren().addAll(title, profitStatistics, bookingStatistics);
    }

    @Override
    public void update() {
        profitStatistics.update();
        bookingStatistics.update();
    }
}

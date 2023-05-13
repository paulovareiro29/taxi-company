package pt.ipvc.views.screens;

import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.layout.items.ProfitStatistics;

public class HomeScreen extends Screen {

    private final ProfitStatistics profitStatistics;

    public HomeScreen() {
        Heading title = new Heading("Home page");

        profitStatistics = new ProfitStatistics();

        this.getChildren().addAll(title, profitStatistics);
    }

    @Override
    public void update() {
        profitStatistics.update();
    }
}

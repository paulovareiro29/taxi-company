package pt.ipvc.views.screens;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.layout.items.BookingStatistics;
import pt.ipvc.layout.items.ProfitStatistics;

public class HomeScreen extends Screen {

    private final ProfitStatistics profitStatistics;
    private final BookingStatistics bookingStatistics;

    public HomeScreen() {
        Heading title = new Heading("Home page");


        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setPercentWidth(50);

        GridPane grid = new GridPane();
        grid.getColumnConstraints().addAll(col1, col2);
        grid.setHgap(8);
        grid.setVgap(8);

        profitStatistics = new ProfitStatistics();
        bookingStatistics = new BookingStatistics();

        VBox left = new VBox(8);
        left.getChildren().add(profitStatistics);

        VBox right = new VBox(8);
        right.getChildren().add(bookingStatistics);

        grid.add(left,0,0);
        grid.add(right, 1, 0);

        this.getChildren().addAll(title, grid);
    }

    @Override
    public void update() {
        profitStatistics.update();
        bookingStatistics.update();
    }
}

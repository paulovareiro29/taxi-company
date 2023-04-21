package pt.ipvc.views.screens;

import javafx.scene.control.Label;
import pt.ipvc.base.Screen;

public class HomeScreen extends Screen {

    public HomeScreen() {
        super();

        Label label = new Label("Home page");
        this.getChildren().add(label);
    }

    @Override
    public void update() {

    }
}

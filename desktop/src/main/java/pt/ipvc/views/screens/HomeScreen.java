package pt.ipvc.views.screens;

import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;

public class HomeScreen extends Screen {

    public HomeScreen() {
        Heading title = new Heading("Home page");
        this.getChildren().add(title);
    }

    @Override
    public void update() {

    }
}

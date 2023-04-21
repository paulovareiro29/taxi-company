package pt.ipvc.views.screens;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;

public class UsersScreen extends Screen {

    public UsersScreen() {
        Heading title = new Heading("Users");

        HBox header = new HBox();
        header.getChildren().addAll(title);

        this.getChildren().add(title);
    }

    @Override
    public void update() {

    }
}

package pt.ipvc.views;

import javafx.scene.control.Label;
import pt.ipvc.base.Content;

public class HomeContent extends Content {

    public HomeContent() {
        super();

        Label label = new Label("Home page");
        this.getChildren().add(label);
    }

    @Override
    public void update() {

    }
}

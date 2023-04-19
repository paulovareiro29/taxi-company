package pt.ipvc.layout;

import javafx.scene.layout.VBox;
import pt.ipvc.base.UIComponent;

public class Sidebar extends VBox implements UIComponent {

    public Sidebar() {
        this.setPrefWidth(240);
        this.getStyleClass().add("sidebar");
    }

    @Override
    public void update() {

    }
}

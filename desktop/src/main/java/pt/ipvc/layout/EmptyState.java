package pt.ipvc.layout;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import pt.ipvc.components.Text;

public class EmptyState extends HBox {

    public EmptyState(String message) {
        setAlignment(Pos.CENTER);

        Text text = new Text(message);
        text.getStyleClass().add("empty-state");
        getChildren().add(text);
    }
}

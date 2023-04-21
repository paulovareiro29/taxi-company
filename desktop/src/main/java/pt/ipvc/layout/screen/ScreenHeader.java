package pt.ipvc.layout.screen;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.UIComponent;

public class ScreenHeader extends HBox implements UIComponent {

    private final HBox left;
    private final HBox right;

    public ScreenHeader() {
        getStyleClass().add("screen__header");
        setSpacing(16);

        left = new HBox(8);
        right = new HBox(8);

        left.setAlignment(Pos.CENTER_LEFT);
        right.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);

        getChildren().addAll(left,right);
    }

    public void addChildrenToLeft(Node ...child) {
        left.getChildren().addAll(child);
    }

    public void addChildrenToRight(Node ...child) {
        right.getChildren().addAll(child);
    }

    @Override
    public void update() {

    }
}

package pt.ipvc.base;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Popup extends javafx.stage.Popup {

    private final VBox container;
    private final Rectangle overlay;

    public Popup(String title) {
        this.setAutoHide(true);
        this.setAutoFix(true);
        this.setAnchorLocation(AnchorLocation.CONTENT_TOP_LEFT);

        overlay = new Rectangle(0, 0);
        overlay.setOnMouseClicked(e -> this.hide());

        Label titleLabel = new Label(title);

        container = new VBox(8);
        container.getChildren().add(titleLabel);

        Group group = new Group();
        group.getChildren().add(container);

        StackPane pane = new StackPane(overlay, group);

        pane.getStyleClass().add("popup");
        overlay.getStyleClass().add("popup__overlay");
        container.getStyleClass().add("popup__container");
        titleLabel.getStyleClass().add("popup__title");

        this.getContent().add(pane);
    }

    public void addChild(Node child) {
        container.getChildren().add(child);
    }

    public void show(Stage owner) {
        overlay.setWidth(owner.getWidth() - 254);
        overlay.setHeight(owner.getHeight() - 102);

        setAnchorX(owner.getX() + 246);
        setAnchorY(owner.getY() + 94);

        super.show(owner);
    }

}

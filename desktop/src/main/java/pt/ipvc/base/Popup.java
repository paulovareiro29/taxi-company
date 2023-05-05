package pt.ipvc.base;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public abstract class Popup extends javafx.stage.Popup implements UIComponent{

    protected final Label header;
    protected final VBox container;
    protected final Rectangle overlay;
    protected final EventListener listener;

    public Popup(String title, EventListener listener) {
        this.setAutoHide(true);
        this.setAutoFix(true);
        this.setAnchorLocation(AnchorLocation.CONTENT_TOP_LEFT);

        this.listener = listener;

        overlay = new Rectangle(0, 0);
        overlay.setOnMouseClicked(e -> this.hide());

        header = new Label(title);

        container = new VBox(8);
        container.getChildren().add(header);

        Group group = new Group();
        group.getChildren().add(container);

        StackPane pane = new StackPane(overlay, group);

        pane.getStyleClass().add("popup");
        overlay.getStyleClass().add("popup__overlay");
        container.getStyleClass().add("popup__container");
        header.getStyleClass().add("popup__title");

        this.getContent().add(pane);
    }

    public void addChildren(Node ...child) {
        container.getChildren().addAll(child);
    }

    public void show(Stage owner) {
        overlay.setWidth(owner.getWidth() - 254);
        overlay.setHeight(owner.getHeight() - 102);

        setAnchorX(owner.getX() + 246);
        setAnchorY(owner.getY() + 94);

        update();
        super.show(owner);
    }

    public void setTitle(String title) {
        header.setText(title);
    }
}

package pt.ipvc.layout;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pt.ipvc.base.UIComponent;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.dal.User;

import java.util.Objects;

public class Topbar extends HBox implements UIComponent {

    private final Label userLabel;

    public Topbar() {
        this.getStyleClass().add("top-bar");
        this.setPrefHeight(32);

        VBox leftGroup = new VBox();
        leftGroup.setAlignment(Pos.CENTER_LEFT);
        ImageView logo = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/logo.png"))));
        logo.setPreserveRatio(true);
        logo.setFitHeight(this.getPrefHeight());
        leftGroup.getChildren().add(logo);

        VBox rightGroup = new VBox();
        rightGroup.setAlignment(Pos.CENTER_RIGHT);

        userLabel = new Label("Hello!");
        userLabel.getStyleClass().add("top-bar__label");
        rightGroup.getChildren().add(userLabel);


        this.getChildren().addAll(leftGroup, rightGroup);
        for(Node node : this.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }

    }

    @Override
    public void update() {
        User authenticatedUser = SessionBLL.getAuthenticatedUser();
        userLabel.setText("Hello " + (authenticatedUser != null ? authenticatedUser.getName() : "null") + "!");
    }
}

package pt.ipvc.components.sidebar;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SidebarButton extends Button {

    public SidebarButton(String text, String icon, EventHandler<javafx.event.ActionEvent> event) {
        this.getStyleClass().add("sidebar__button");
        this.setMaxWidth(Double.MAX_VALUE);
        this.setOnAction(event);

        ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/icons/" + icon))));
        this.setGraphic(image);
        this.setText(text);
        this.setGraphicTextGap(8);
    }
}

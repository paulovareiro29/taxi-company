package pt.ipvc.components.buttons;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ButtonIcon extends Button {

    public ButtonIcon(String icon) {
        super("", ButtonSize.ICON);

        ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/icons/" + icon))));
        image.setPreserveRatio(true);
        image.setFitWidth(16);
        setAlignment(Pos.CENTER);
        setGraphic(image);
    }
}

package pt.ipvc.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Icon extends ImageView {

    public Icon(String icon) {
        super();
        setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/icons/" + icon))));
    }
}

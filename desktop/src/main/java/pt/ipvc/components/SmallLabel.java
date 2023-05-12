package pt.ipvc.components;

import javafx.scene.control.Label;

public class SmallLabel extends Label {

    public SmallLabel(String text) {
        super(text);
        getStyleClass().add("small-label");
    }
}

package pt.ipvc.components;

import javafx.scene.control.Label;

public class Heading extends Label {

    public Heading(String text) {
        super(text);
        getStyleClass().add("heading");
    }
}

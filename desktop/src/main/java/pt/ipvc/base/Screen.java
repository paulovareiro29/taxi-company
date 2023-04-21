package pt.ipvc.base;

import javafx.scene.layout.VBox;

public abstract class Screen extends VBox implements UIComponent{

    public Screen() {
        getStyleClass().add("screen");
        setSpacing(16);
    }
}

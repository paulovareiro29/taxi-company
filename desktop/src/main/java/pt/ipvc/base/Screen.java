package pt.ipvc.base;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public abstract class Screen extends VBox implements UIComponent{

    public Screen() {
        this.setPadding(new Insets(16));
    }
}

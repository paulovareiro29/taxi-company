package pt.ipvc.base;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public abstract class Content extends VBox implements UIComponent{

    public Content() {
        this.setPadding(new Insets(16));
    }
}

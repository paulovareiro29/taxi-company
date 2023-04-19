package pt.ipvc.base;

import javafx.scene.layout.AnchorPane;
import pt.ipvc.config.Config;

public abstract class Scene extends javafx.scene.Scene implements UIComponent {

    public Scene() {
        super(new AnchorPane(), Config.getWindowWidth(), Config.getWindowHeight());
        this.getStylesheets().add("/pt/ipvc/styles/global.css");
    }

}

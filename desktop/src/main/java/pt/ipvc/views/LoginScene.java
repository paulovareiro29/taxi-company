package pt.ipvc.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import pt.ipvc.config.Config;

import java.io.IOException;

public class LoginScene extends Scene{

    public LoginScene() throws IOException {
        super(new AnchorPane(), Config.getWindowWidth(), Config.getWindowHeight());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login-view.fxml"));
        loader.setController(this);
        setRoot(loader.load());
    }

}

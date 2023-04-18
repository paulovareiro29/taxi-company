package pt.ipvc;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.ipvc.config.Config;
import pt.ipvc.handlers.SceneHandler;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.load();

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        SceneHandler.load(stage);
    }
}

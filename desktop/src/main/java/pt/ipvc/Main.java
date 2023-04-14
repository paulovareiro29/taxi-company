package pt.ipvc;

import javafx.application.Application;
import javafx.stage.Stage;
import pt.ipvc.config.Config;
import pt.ipvc.views.LoginScene;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        config.load();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Taxi Company Management");
        stage.setScene(new LoginScene());
        stage.show();
    }
}

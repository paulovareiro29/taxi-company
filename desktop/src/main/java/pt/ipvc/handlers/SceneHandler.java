package pt.ipvc.handlers;

import javafx.stage.Stage;
import pt.ipvc.base.Scene;
import pt.ipvc.views.DashboardScene;
import pt.ipvc.views.LoginScene;

import java.util.HashMap;

public class SceneHandler {
    private static HashMap<ScenesEnum, Scene> scenes;
    public static Stage stage;

    public static void load(Stage stage) {
        loadScenes();

        SceneHandler.stage = stage;
        SceneHandler.stage.setTitle("Taxi Company Management");
        SceneHandler.stage.setScene(scenes.get(ScenesEnum.LOGIN));
        SceneHandler.stage.show();
    }

    private static void loadScenes() {
        scenes = new HashMap<>();
        scenes.put(ScenesEnum.LOGIN, new LoginScene());
        scenes.put(ScenesEnum.DASHBOARD, new DashboardScene());
    }

    public static void changeScene(ScenesEnum key) {
        stage.setScene(scenes.get(key));
    }

}

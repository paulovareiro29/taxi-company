package pt.ipvc.handlers;

import javafx.stage.Stage;
import pt.ipvc.base.Content;
import pt.ipvc.base.Scene;
import pt.ipvc.views.DashboardScene;
import pt.ipvc.views.HomeContent;
import pt.ipvc.views.LoginScene;

import java.util.HashMap;

public class SceneHandler {
    private static HashMap<ScenesEnum, Scene> scenes;
    private static HashMap<ContentsEnum, Content> contents;
    public static Stage stage;

    public static void load(Stage stage) {
        loadScenes();
        loadContents();

        SceneHandler.changeDashboardContent(ContentsEnum.HOME);

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

    private static void loadContents() {
        contents = new HashMap<>();
        contents.put(ContentsEnum.HOME, new HomeContent());
    }

    public static void changeScene(ScenesEnum key) {
        scenes.get(key).update();
        stage.setScene(scenes.get(key));
    }

    public static void changeDashboardContent(ContentsEnum key) {
        DashboardScene dashboard = (DashboardScene) scenes.get(ScenesEnum.DASHBOARD);
        dashboard.changeContent(contents.get(key));
        dashboard.update();
    }

}

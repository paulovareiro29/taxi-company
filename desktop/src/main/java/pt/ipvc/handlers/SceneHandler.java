package pt.ipvc.handlers;

import javafx.stage.Stage;
import pt.ipvc.base.Scene;
import pt.ipvc.base.Screen;
import pt.ipvc.views.DashboardScene;
import pt.ipvc.views.LoginScene;
import pt.ipvc.views.screens.HomeScreen;
import pt.ipvc.views.screens.UsersScreen;
import pt.ipvc.views.screens.VehiclesScreen;

import java.util.HashMap;

public class SceneHandler {
    private static HashMap<ScenesEnum, Scene> scenes;
    private static HashMap<ScreensEnum, Screen> screens;
    public static Stage stage;

    public static void load(Stage stage) {
        loadScenes();
        loadScreens();

        SceneHandler.changeScreen(ScreensEnum.HOME);

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

    private static void loadScreens() {
        screens = new HashMap<>();
        screens.put(ScreensEnum.HOME, new HomeScreen());
        screens.put(ScreensEnum.USERS, new UsersScreen());
        screens.put(ScreensEnum.VEHICLES, new VehiclesScreen());
    }

    public static void changeScene(ScenesEnum key) {
        scenes.get(key).update();
        stage.setScene(scenes.get(key));
    }

    public static void changeScreen(ScreensEnum key) {
        DashboardScene dashboard = (DashboardScene) scenes.get(ScenesEnum.DASHBOARD);
        dashboard.changeScreen(screens.get(key));
        dashboard.update();
    }

}

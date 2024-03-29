package pt.ipvc.handlers;

import javafx.stage.Stage;
import pt.ipvc.base.Scene;
import pt.ipvc.base.Screen;
import pt.ipvc.views.DashboardScene;
import pt.ipvc.views.LoginScene;
import pt.ipvc.views.screens.*;

import java.util.HashMap;

public class SceneHandler {
    private static HashMap<ScenesEnum, Scene> scenes;
    private static HashMap<ScreensEnum, Screen> screens;
    public static Stage stage;

    private static ScreensEnum currentScreen;

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
        screens.put(ScreensEnum.BRANDS, new BrandsScreen());
        screens.put(ScreensEnum.BOOKINGS, new BookingsScreen());
        screens.put(ScreensEnum.VIEW_BOOKING, new ViewBookingScreen());
        screens.put(ScreensEnum.PAYMENT_TYPES, new PaymentMethodsScreen());
        screens.put(ScreensEnum.FEEDBACKS, new FeedBacksScreen());
    }

    public static void changeScene(ScenesEnum key) {
        scenes.get(key).update();
        stage.setScene(scenes.get(key));
    }

    public static void changeScreen(ScreensEnum key) {
        DashboardScene dashboard = (DashboardScene) scenes.get(ScenesEnum.DASHBOARD);
        dashboard.changeScreen(screens.get(key));
        currentScreen = key;
        dashboard.update();
    }

    public static void updateScreen(ScreensEnum key) {
        screens.get(key).update();
    }

    public static ScreensEnum getCurrentScreen() {
        return currentScreen;
    }
}

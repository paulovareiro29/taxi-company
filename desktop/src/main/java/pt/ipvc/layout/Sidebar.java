package pt.ipvc.layout;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pt.ipvc.base.UIComponent;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.components.sidebar.SidebarButton;
import pt.ipvc.components.sidebar.SidebarSeparator;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScenesEnum;
import pt.ipvc.handlers.ScreensEnum;

import java.util.ArrayList;
import java.util.List;

public class Sidebar extends VBox implements UIComponent {

    ArrayList<SidebarButton> items;

    public Sidebar() {
        this.getStyleClass().add("sidebar");
        SidebarButton homeButton = new SidebarButton("Home","dashboard.png", List.of(RoleBLL.getAdminRole(), RoleBLL.getSecretaryRole()), e -> changeRoute(ScreensEnum.HOME));
        SidebarButton usersButton = new SidebarButton("Users","double-users.png", List.of(RoleBLL.getAdminRole(), RoleBLL.getSecretaryRole()), e -> changeRoute(ScreensEnum.USERS));
        SidebarButton vehiclesButton = new SidebarButton("Vehicles","car.png", List.of(RoleBLL.getAdminRole()), e -> changeRoute(ScreensEnum.VEHICLES));
        SidebarButton bookingsButton = new SidebarButton("Bookings","booking.png", List.of(RoleBLL.getAdminRole(), RoleBLL.getSecretaryRole()), e -> changeRoute(ScreensEnum.BOOKINGS));
        SidebarButton methodsButton = new SidebarButton("Payment Methods","credit-card.png", List.of(RoleBLL.getAdminRole()), e -> changeRoute(ScreensEnum.PAYMENT_TYPES));
        SidebarButton logoutButton = new SidebarButton("Logout", "logout.png", List.of(RoleBLL.getAdminRole(), RoleBLL.getSecretaryRole()), e -> {
            SceneHandler.changeScene(ScenesEnum.LOGIN);
            SessionBLL.logout();
        });

        items = new ArrayList<>();
        items.add(homeButton);
        items.add(usersButton);
        items.add(vehiclesButton);
        items.add(bookingsButton);
        items.add(methodsButton);
        items.add(logoutButton);

        VBox main = new VBox(8);
        VBox.setVgrow(main, Priority.ALWAYS);
        VBox secondary = new VBox(8);

        ArrayList<Node> MainItems = new ArrayList<>();
        MainItems.add(homeButton);
        MainItems.add(usersButton);
        MainItems.add(vehiclesButton);
        MainItems.add(bookingsButton);
        MainItems.add(methodsButton);
        MainItems.add(new SidebarSeparator());

        ArrayList<Node> SecondaryItems = new ArrayList<>();
        SecondaryItems.add(logoutButton);


        for(Node item : MainItems) {
            HBox.setHgrow(item, Priority.ALWAYS);
            main.getChildren().add(item);
        }

        for(Node item : SecondaryItems) {
            HBox.setHgrow(item, Priority.ALWAYS);
            secondary.getChildren().add(item);
        }

        this.getChildren().addAll(main, secondary);
    }

    private void changeRoute(ScreensEnum screen) {
        SceneHandler.changeScreen(screen);
    }

    @Override
    public void update() {
        for(SidebarButton item : items) {
            item.update();
        }
    }
}

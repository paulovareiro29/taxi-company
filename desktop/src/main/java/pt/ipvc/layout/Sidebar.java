package pt.ipvc.layout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pt.ipvc.base.UIComponent;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.components.sidebar.SidebarButton;
import pt.ipvc.components.sidebar.SidebarSeparator;
import pt.ipvc.handlers.ContentsEnum;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScenesEnum;

import java.util.ArrayList;

public class Sidebar extends VBox implements UIComponent {

    public Sidebar() {
        this.getStyleClass().add("sidebar");

        VBox main = new VBox(8);
        VBox.setVgrow(main, Priority.ALWAYS);
        VBox secondary = new VBox(8);

        ArrayList<Node> MainItems = new ArrayList<>();
        MainItems.add(new SidebarButton("Home","dashboard.png", e -> changeRoute(ContentsEnum.HOME)));
        MainItems.add(new SidebarSeparator());

        ArrayList<Node> SecondaryItems = new ArrayList<>();
        SecondaryItems.add(new SidebarButton("Logout", "logout.png",e -> {
            SceneHandler.changeScene(ScenesEnum.LOGIN);
            SessionBLL.logout();
        }));


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

    private void changeRoute(ContentsEnum content) {
        SceneHandler.changeDashboardContent(content);
    }

    @Override
    public void update() {

    }
}

package pt.ipvc.views;

import javafx.scene.layout.BorderPane;
import pt.ipvc.base.Content;
import pt.ipvc.base.Scene;
import pt.ipvc.base.UIComponent;
import pt.ipvc.layout.Sidebar;
import pt.ipvc.layout.Topbar;

import java.util.HashMap;

public class DashboardScene extends Scene {

    private final HashMap<String, UIComponent> children = new HashMap<>();
    protected BorderPane root;
    protected Sidebar sidebar;
    protected Topbar topbar;

    public DashboardScene() {
        super();

        root = new BorderPane();
        sidebar = new Sidebar();
        topbar = new Topbar();

        root.setTop(topbar);
        root.setLeft(sidebar);

        setRoot(root);
        children.put("topbar",topbar);
        children.put("sidebar",sidebar);
    }

    public void changeContent(Content content) {
        root.setCenter(content);
        children.put("content", content);
    }

    @Override
    public void update() {
        if (children == null) return;
        for(UIComponent child : children.values()) {
            child.update();
        }
    }
}

package pt.ipvc.components.sidebar;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipvc.base.UIComponent;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.dal.Role;

import java.util.List;
import java.util.Objects;

public class SidebarButton extends Button implements UIComponent {

    private List<Role> roles;

    public SidebarButton(String text, String icon, List<Role> roles, EventHandler<javafx.event.ActionEvent> event) {
        this.getStyleClass().add("sidebar__button");
        this.setMaxWidth(Double.MAX_VALUE);
        this.setOnAction(event);

        ImageView image = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/icons/" + icon))));
        this.setGraphic(image);
        this.setText(text);
        this.setGraphicTextGap(8);

        this.roles = roles;
    }

    @Override
    public void update() {
        boolean hasPermission = false;

        if(SessionBLL.getAuthenticatedUser() != null) {
            for (Role role : roles) {
                hasPermission = role.getName().equalsIgnoreCase(SessionBLL.getAuthenticatedUser().getRole().getName());
                if (hasPermission) break;
            }
        }

        setVisible(hasPermission);
        setManaged(hasPermission);
    }
}

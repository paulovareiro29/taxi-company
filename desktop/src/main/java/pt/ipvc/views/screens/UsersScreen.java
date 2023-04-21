package pt.ipvc.views.screens;

import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.layout.screen.ScreenHeader;

public class UsersScreen extends Screen {

    public UsersScreen() {
        Heading title = new Heading("Users");
        Button newUserButton = new Button("Add new");

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(newUserButton);

        this.getChildren().add(header);
    }

    @Override
    public void update() {

    }
}

package pt.ipvc.views.screens;

import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.UsersTable;

public class UsersScreen extends Screen {

    public UsersScreen() {
        Heading title = new Heading("Users");
        Button newUserButton = new Button("Add new");

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(newUserButton);

        UsersTable table = new UsersTable();

        getChildren().addAll(header, table);
    }

    @Override
    public void update() {

    }
}

package pt.ipvc.views.screens;

import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.CreateUserPopup;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.UsersTable;

public class UsersScreen extends Screen {

    public UsersScreen() {

        /* POPUP */
        Popup createUserPopup = new CreateUserPopup();

        /* HEADER */
        Heading title = new Heading("Users");
        Button newUserButton = new Button("Add new");
        newUserButton.setOnAction(e -> createUserPopup.show(SceneHandler.stage));

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(newUserButton);

        /* TABLE */
        UsersTable table = new UsersTable();


        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, table);
    }

    @Override
    public void update() {

    }
}

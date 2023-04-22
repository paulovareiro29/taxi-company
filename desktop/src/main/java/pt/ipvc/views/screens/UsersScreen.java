package pt.ipvc.views.screens;

import pt.ipvc.base.EventListener;
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



        /* HEADER */
        Heading title = new Heading("Users");
        Button newUserButton = new Button("Add new");

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(newUserButton);

        /* TABLE */
        UsersTable table = new UsersTable();

        /* POPUP */
        Popup createUserPopup = new CreateUserPopup(new EventListener() {
            @Override
            public void onSuccess() {
                table.refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });

        newUserButton.setOnAction(e -> createUserPopup.show(SceneHandler.stage));


        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, table);
    }

    @Override
    public void update() {

    }
}

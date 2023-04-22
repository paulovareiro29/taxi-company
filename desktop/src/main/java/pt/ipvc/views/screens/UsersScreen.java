package pt.ipvc.views.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.CreateUserPopup;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.UsersTable;
import pt.ipvc.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersScreen extends Screen {

    public UsersScreen() {


        /* TABLE */
        UsersTable table = new UsersTable();


        /* HEADER */
        Heading title = new Heading("Users");
        Button newUserButton = new Button("Add new");


        TextField userFilter = new TextField();
        userFilter.setPromptText("Search");
        userFilter.setIcon("search_secondary.png");

        Group searchBar = new Group(userFilter);

        /* ROLE COMBOBOX */
        List<ComboItem> availableRoles = RoleBLL.index().stream().map(role -> new ComboItem(StringUtils.capitalize(role.getName()), () -> {
            table.setRoleFilter(role.getName().toLowerCase());
            table.refresh();
        })).collect(Collectors.toList());
        availableRoles.add(0,new ComboItem("All", () -> {
            table.setRoleFilter("");
            table.refresh();
        }));
        ComboBox roleFilter = new ComboBox(availableRoles);
        roleFilter.setPromptText("Filter by role");


        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(roleFilter,searchBar, newUserButton);


        userFilter.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    table.setNameFilter(newValue.toLowerCase());
                    table.setEmailFilter(newValue.toLowerCase());
                    table.refresh();
                });

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

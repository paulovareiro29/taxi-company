package pt.ipvc.views.screens;

import javafx.scene.Group;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Screen;
import pt.ipvc.components.Heading;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.user.CreateUserPopup;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.layout.table.FeedBacksTable;
import pt.ipvc.layout.table.UsersTable;

import java.util.List;
import java.util.stream.Collectors;

public class FeedBacksScreen extends Screen {

    public FeedBacksScreen() {


        /* TABLE */
        FeedBacksTable table = new FeedBacksTable();


        /* HEADER */
        Heading title = new Heading("Trip Feedbacks");
        Button newUserButton = new Button("Add new");


        TextField userFilter = new TextField();
        userFilter.setPromptText("Search");
        userFilter.setIcon("search--secondary.png");

        Group searchBar = new Group(userFilter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);



        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, table);
    }

    @Override
    public void update() {

    }
}

package pt.ipvc.views.screens;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.FeedbackBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.ScrollPane;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.layout.EmptyState;
import pt.ipvc.layout.items.FeedbackItem;
import pt.ipvc.layout.screen.ScreenHeader;

public class FeedBacksScreen extends Screen {

    private final VBox container;

    public FeedBacksScreen() {

        /* HEADER */
        Heading title = new Heading("Trip Feedbacks");


        TextField userFilter = new TextField();
        userFilter.setPromptText("Search");
        userFilter.setIcon("search--secondary.png");

        Group searchBar = new Group(userFilter);

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);

        container = new VBox(8);
        scrollPane.setContent(container);

        refresh();

        /* ADD EVERYTHING TO SCREEN */
        getChildren().addAll(header, scrollPane);
    }

    private void refresh() {
        container.getChildren().clear();
        FeedbackBLL.index()
                .forEach(feedback -> {
                    container.getChildren().add(new FeedbackItem(feedback));
                });

        if(container.getChildren().size() == 0){
            container.getChildren().add(new EmptyState("No feedbacks found"));

        }
    }

    @Override
    public void update() {
        refresh();
    }
}

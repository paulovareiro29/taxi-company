package pt.ipvc.views.screens;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.FeedbackBLL;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.ScrollPane;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.layout.EmptyState;
import pt.ipvc.layout.items.FeedbackItem;
import pt.ipvc.layout.screen.ScreenHeader;
import pt.ipvc.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedBacksScreen extends Screen {

    private final VBox container;
    private int ratingFilter = -1;
    private String filter;

    public FeedBacksScreen() {

        /* HEADER */
        Heading title = new Heading("Trip Feedbacks");


        TextField userFilter = new TextField();
        userFilter.setPromptText("Search");
        userFilter.setIcon("search--secondary.png");

        userFilter.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    filter = newValue;
                    refresh();
                });

        Group searchBar = new Group(userFilter);

        /* ROLE COMBOBOX */
        List<ComboItem> availableRatings = Stream.of(1,2,3,4,5).map(rating -> new ComboItem("" + rating, () -> {
            ratingFilter = rating;
            refresh();
        })).collect(Collectors.toList());
        availableRatings.add(0,new ComboItem("All", () -> {
            ratingFilter = -1;
            refresh();
        }));
        ComboBox ratingFilter = new ComboBox(availableRatings);
        ratingFilter.setPromptText("Filter by rating");

        ScreenHeader header = new ScreenHeader();
        header.addChildrenToLeft(title);
        header.addChildrenToRight(ratingFilter, searchBar);

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
                .stream()
                .filter(f -> (ratingFilter == -1 || f.getRating() == ratingFilter) && f.getClient().getEmail().toLowerCase().contains(filter != null ? filter.toLowerCase() : ""))
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

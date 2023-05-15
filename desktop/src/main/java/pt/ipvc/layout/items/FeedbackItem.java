package pt.ipvc.layout.items;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipvc.components.Icon;
import pt.ipvc.components.SmallLabel;
import pt.ipvc.components.Text;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Feedback;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;
import pt.ipvc.layout.screen.SpaceBetweenRow;
import pt.ipvc.views.screens.ViewBookingScreen;

public class FeedbackItem extends Card {

    public final Feedback data;

    public FeedbackItem(Feedback data) {
        this.data = data;
        setSpacing(12);

        if(data == null) {
            getChildren().add(new Text("No feedback found"));
            return;
        }

        // ------------

        SmallLabel ratingLabel = new SmallLabel("Rating");
        HBox ratingHBox = new HBox();
        for(int i = 0; i < data.getRating(); i++) {
            ratingHBox.getChildren().add(new Icon("star--warning.png"));
        }

        for(int i = 0; i < 5 - data.getRating(); i++) {
            ratingHBox.getChildren().add(new Icon("star--secondary.png"));
        }

        VBox ratingBox = new VBox(0);
        ratingBox.getChildren().addAll(ratingLabel, ratingHBox);

        // ------------

        SmallLabel reviewLabel = new SmallLabel("Review");
        Text reviewText = new Text(data.getReview());


        VBox reviewBox = new VBox(0);
        reviewBox.getChildren().addAll(reviewLabel, reviewText);

        // ------------

        SmallLabel clientLabel = new SmallLabel("Written by");
        Text clientText = new Text(data.getClient().getEmail());

        VBox clientBox = new VBox(0);
        clientBox.getChildren().addAll(clientLabel, clientText);

        // ------------

        Button viewTripButton = new Button("View trip");
        viewTripButton.setOnAction(e -> {
            ViewBookingScreen.booking = data.getTrip().getBooking();
            SceneHandler.changeScreen(ScreensEnum.VIEW_BOOKING);
        });

        if(SceneHandler.getCurrentScreen() == ScreensEnum.VIEW_BOOKING) {
            viewTripButton.setVisible(false);
        }

        getChildren().addAll(reviewBox, clientBox, new SpaceBetweenRow(ratingBox, viewTripButton));

    }
}

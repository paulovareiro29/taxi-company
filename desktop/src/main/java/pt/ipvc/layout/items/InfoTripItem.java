package pt.ipvc.layout.items;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import pt.ipvc.components.SmallLabel;
import pt.ipvc.components.Text;
import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Trip;

public class InfoTripItem extends Card {


    public InfoTripItem(Trip data) {
        setSpacing(24);

        if(data == null) {
            getChildren().add(new Text("No trip found"));
            return;
        }

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setPercentWidth(50);

        // -------
        SmallLabel driverLabel = new SmallLabel("Driver");
        Text driverText = new Text(data.getEmployee().getEmail());

        VBox driverBox = new VBox(0);
        driverBox.getChildren().addAll(driverLabel, driverText);
        // -------

        SmallLabel pickupLabel = new SmallLabel("Pickup Date");
        Text pickupText = new Text(data.getPickupDate().toString());

        VBox pickupBox = new VBox(0);
        pickupBox.getChildren().addAll(pickupLabel, pickupText);
        // -------
        SmallLabel dropoffLabel = new SmallLabel("Dropoff Date");
        Text dropoffText = new Text(data.getDropoffDate().toString());

        VBox dropoffBox = new VBox(0);
        dropoffBox.getChildren().addAll(dropoffLabel, dropoffText);
        // -------
        GridPane datesBox = new GridPane();
        datesBox.getColumnConstraints().addAll(col1, col2);
        datesBox.add(pickupBox,0,0);
        datesBox.add(dropoffBox,1,0);
        // -------

        SmallLabel priceLabel = new SmallLabel("Price");
        Text priceText = new Text(data.getPrice().toString() + " €");

        VBox priceBox = new VBox(0);
        priceBox.getChildren().addAll(priceLabel, priceText);
        // -------

        getChildren().addAll(driverBox, datesBox, priceBox);
    }
}

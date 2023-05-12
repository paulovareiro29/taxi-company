package pt.ipvc.layout.items;

import javafx.scene.layout.*;
import pt.ipvc.components.SmallLabel;
import pt.ipvc.components.Text;
import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Booking;
import pt.ipvc.utils.StringUtils;

public class InfoBookingItem extends Card {

    public final Booking data;

    public InfoBookingItem(Booking data) {
        this.data = data;
        setSpacing(24);

        if(data == null) {
            getChildren().add(new Text("No booking found"));
            return;
        }

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setPercentWidth(50);

        // ---------

        SmallLabel clientLabel = new SmallLabel("Client");
        Text clientText = new Text(data.getClient().getEmail());

        VBox clientBox = new VBox(0);
        clientBox.getChildren().addAll(clientLabel, clientText);

        SmallLabel statusLabel = new SmallLabel("Status");
        Text statusText = new Text(StringUtils.capitalize(data.getState().getName()));

        VBox statusBox = new VBox(0);
        statusBox.getChildren().addAll(statusLabel, statusText);

        GridPane headerBox = new GridPane();
        headerBox.getColumnConstraints().addAll(col1, col2);
        headerBox.add(clientBox,0,0);
        headerBox.add(statusBox,1,0);


        // -------
        SmallLabel originLabel = new SmallLabel("Origin");
        Text originText = new Text(data.getOrigin());

        VBox originBox = new VBox(0);
        originBox.getChildren().addAll(originLabel, originText);
        // -------
        SmallLabel destinationLabel = new SmallLabel("Destination");
        Text destinationText = new Text(data.getDestination());

        VBox destinationBox = new VBox(0);
        destinationBox.getChildren().addAll(destinationLabel, destinationText);
        // -------
        GridPane routeBox = new GridPane();
        routeBox.getColumnConstraints().addAll(col1, col2);
        routeBox.add(originBox,0,0);
        routeBox.add(destinationBox,1,0);
        // -------

        SmallLabel occupancyLabel = new SmallLabel("Occupancy");
        Text occupancyText = new Text("" + data.getOccupancy());

        VBox occupancyBox = new VBox();
        occupancyBox.getChildren().addAll(occupancyLabel, occupancyText);
        // -------

        SmallLabel extraLabel = new SmallLabel("Extra");
        Text extraText = new Text(data.getExtra());
        if(extraText.getText().isBlank()) extraText.setText("No extra message");

        VBox extraBox = new VBox();
        extraBox.getChildren().addAll(extraLabel, extraText);
        // -------
        SmallLabel pickupDateLabel = new SmallLabel("Pickup date");
        Text pickupDateText = new Text(data.getPickupDate().toString());

        VBox pickupDateBox = new VBox();
        pickupDateBox.getChildren().addAll(pickupDateLabel, pickupDateText);
        // -------

        SmallLabel taxiPlateLabel = new SmallLabel("Taxi Plate");
        Text taxiPlateText = new Text(data.getTaxi().getLicensePlate());

        VBox taxiPlateBox = new VBox();
        taxiPlateBox.getChildren().addAll(taxiPlateLabel, taxiPlateText);


        SmallLabel taxiModelLabel = new SmallLabel("Taxi Model");
        Text taxiModelText = new Text(data.getTaxi().getModel().getBrand().getName() + " - " + data.getTaxi().getModel().getName());

        VBox taxiModelBox = new VBox();
        taxiModelBox.getChildren().addAll(taxiModelLabel, taxiModelText);
        // -------

        GridPane taxiBox = new GridPane();
        taxiBox.getColumnConstraints().addAll(col1, col2);
        taxiBox.add(taxiPlateBox,0,0);
        taxiBox.add(taxiModelBox,1,0);

        getChildren().addAll(headerBox, routeBox, occupancyBox, extraBox, pickupDateBox, taxiBox);
    }
}

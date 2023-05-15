package pt.ipvc.layout.items;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.ComboItem;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.BookingStateBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.card.Card;
import pt.ipvc.components.inputs.AutoCompleteComboBox;
import pt.ipvc.components.inputs.DateTimePicker;
import pt.ipvc.components.inputs.NumericField;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Taxi;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PendingBookingItem extends Card {

    public final Booking data;

    private final TextField clientField;
    private final TextField originField;
    private final TextField destinationField;
    private final NumericField occupancyField;
    private final TextField extraField;
    private final DateTimePicker pickupDateField;
    private final AutoCompleteComboBox taxiField;
    private final Label pickupDateErrorLabel;
    private final Label errorLabel;

    private Taxi selectedTaxi;

    public PendingBookingItem(Booking data) {
        this.data = data;
        setSpacing(8);

        clientField = new TextField(data.getClient().getEmail());
        clientField.getInput().setEditable(false);
        clientField.setIcon("user--secondary.png");

        originField = new TextField(data.getOrigin());
        originField.setPromptText("Origin");
        originField.setIcon("marker--secondary.png");

        destinationField = new TextField(data.getDestination());
        destinationField.setPromptText("Destination");
        destinationField.setIcon("marker--secondary.png");

        occupancyField = new NumericField(data.getOccupancy());
        occupancyField.setPromptText("Occupancy");
        occupancyField.setIcon("double-users--secondary.png");

        extraField = new TextField(data.getExtra());
        extraField.setPromptText("Extra");
        extraField.setIcon("message--secondary.png");

        pickupDateField = new DateTimePicker();
        pickupDateField.setDateTimeValue(data.getPickupDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        pickupDateField.setPrefWidth(Double.MAX_VALUE);
        pickupDateField.setPromptText("Pickup date");
        pickupDateField.disablePastDates();

        taxiField = new AutoCompleteComboBox(Collections.emptyList());
        taxiField.setPrefWidth(Double.MAX_VALUE);
        taxiField.setPromptText("Select taxi");

        HBox routeBox = new HBox(8);
        HBox.setHgrow(originField, Priority.ALWAYS);
        HBox.setHgrow(destinationField, Priority.ALWAYS);
        routeBox.getChildren().addAll(originField, destinationField);

        List<Taxi> taxis = TaxiBLL.index();
        taxiField.setItems(taxis.stream()
                        .filter(t -> t.getMaxOccupancy() >= (occupancyField.getText().equals("") ? 0 : Integer.parseInt(occupancyField.getText())))
                        .map(taxi -> new ComboItem(taxi.getLicensePlate(), () -> {
                            selectedTaxi = taxi;
                        }))
                        .collect(Collectors.toList()));

        occupancyField.getInput().textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    int newOccupancy = occupancyField.getText().equals("") ? 0 : Integer.parseInt(occupancyField.getText());

                    List<Taxi> taxisList = TaxiBLL.index();
                    taxiField.setItems(taxisList.stream()
                            .filter(t -> t.getMaxOccupancy() >= newOccupancy)
                            .map(taxi -> new ComboItem(taxi.getLicensePlate(), () -> {
                                selectedTaxi = taxi;
                            }))
                            .collect(Collectors.toList()));
                });

        if(taxis.size() > 0) {
            selectedTaxi = taxis.get(0);
            taxiField.getSelectionModel().select(0);
        }

        pickupDateErrorLabel = new Label("");
        pickupDateErrorLabel.setVisible(false);
        pickupDateErrorLabel.setStyle("-fx-text-fill: -fx-color-danger");

        errorLabel = new Label("");
        errorLabel.setVisible(false);
        errorLabel.setStyle("-fx-text-fill: -fx-color-danger");


        Button confirmButton = new Button("Confirm booking");
        confirmButton.setOnAction(e -> handleConfirm());

        getChildren().addAll(clientField, routeBox, occupancyField, extraField, pickupDateField, pickupDateErrorLabel, new Separator(), taxiField, errorLabel, confirmButton);
    }

    private void handleConfirm() {
        clearErrors();

        boolean hasError = false;
        StringBuilder dateStringBuilder = new StringBuilder();
        StringBuilder taxiStringBuilder = new StringBuilder();

        if(originField.getText().isBlank()) {
            originField.setError("Origin is required");
            hasError = true;
        }

        if(destinationField.getText().isBlank()) {
            destinationField.setError("Destination is required");
            hasError = true;
        }

        if(pickupDateField.getDateTimeValue() == null) {
            hasError = true;
            dateStringBuilder.append("Select a pickup date\n");
        }

        if(pickupDateField.getDateTimeValue().isBefore(LocalDateTime.now())) {
            hasError = true;
            dateStringBuilder.append("Selected pickup date cannot be in the past\n");
        }

        if(occupancyField.getText().isBlank()) {
            occupancyField.setError("Occupancy is required");
            hasError = true;
        }

        if(selectedTaxi == null) {
            hasError = true;
            taxiStringBuilder.append("Select a taxi");
        }


        if  (hasError) {
            pickupDateErrorLabel.setText(dateStringBuilder.toString());
            pickupDateErrorLabel.setVisible(true);

            errorLabel.setText(taxiStringBuilder.toString());
            errorLabel.setVisible(true);
            return;
        }

        data.setOrigin(originField.getText());
        data.setDestination(destinationField.getText());
        data.setPickupDate(java.util.Date
                .from(pickupDateField.getDateTimeValue().atZone(ZoneId.systemDefault())
                        .toInstant()));
        data.setOccupancy(Integer.parseInt(occupancyField.getText()));
        data.setTaxi(selectedTaxi);
        data.setState(BookingStateBLL.getByName("confirmed"));

        try {
            BookingBLL.update(data);
            SceneHandler.updateScreen(ScreensEnum.VIEW_BOOKING);
            clearErrors();
        } catch(Exception ignore) {}
    }

    private void clearErrors() {
        originField.clearError();
        destinationField.clearError();
        occupancyField.clearError();
        extraField.clearError();

        pickupDateErrorLabel.setText("");
        pickupDateErrorLabel.setVisible(false);

        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

}

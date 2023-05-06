package pt.ipvc.layout.popup.booking;

import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.AutoCompleteComboBox;
import pt.ipvc.components.inputs.NumericField;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.User;
import pt.ipvc.utils.StringUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CreateBookingPopup extends Popup {

    private User selectedClient;

    private final AutoCompleteComboBox clientField;
    private final TextField originField;
    private final TextField destinationField;
    private final TextField pickupDateField;
    private final NumericField occupancyField;
    private final TextField extraField;

    public CreateBookingPopup(EventListener listener) {
        super("New booking", listener);

        clientField = new AutoCompleteComboBox(Collections.emptyList());
        clientField.setPrefWidth(Double.MAX_VALUE);
        clientField.setPromptText("Select client");

        originField = new TextField();
        originField.setPromptText("Origin");
        originField.setIcon("marker--secondary.png");

        destinationField = new TextField();
        destinationField.setPromptText("Destination");
        destinationField.setIcon("marker--secondary.png");

        pickupDateField = new TextField();
        pickupDateField.setPromptText("Pickup date");
        pickupDateField.setIcon("calendar--secondary.png");

        occupancyField = new NumericField();
        occupancyField.setPromptText("Occupancy");
        occupancyField.setIcon("double-users--secondary.png");

        extraField = new TextField();
        extraField.setPromptText("Extra");
        extraField.setIcon("calendar--secondary.png");

        Button cancelButton = new Button("Cancel", ButtonAppearance.outlined_primary);
        Button submitButton = new Button("Create");

        cancelButton.setOnAction(e -> {
            listener.onCancel();
            hide();
        });
        submitButton.setOnAction(e -> handleSubmitButton());

        HBox options = new HBox(8);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        HBox.setHgrow(submitButton, Priority.ALWAYS);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        submitButton.setMaxWidth(Double.MAX_VALUE);

        options.getChildren().addAll(cancelButton, submitButton);
        addChildren(clientField, originField, destinationField, pickupDateField, occupancyField, new Separator(), extraField, options);
    }

    private void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(originField.getText().isBlank()) {
            originField.setError("Origin is required");
            hasError = true;
        }

        if(destinationField.getText().isBlank()) {
            destinationField.setError("Destination is required");
            hasError = true;
        }

        if(pickupDateField.getText().isBlank()) {
            pickupDateField.setError("Pickup date is required");
            hasError = true;
        }

        if(occupancyField.getText().isBlank()) {
            occupancyField.setError("Occupancy is required");
            hasError = true;
        }

        if  (hasError) return;

        try {
            BookingBLL.create(selectedClient, originField.getText(), destinationField.getText(), Date.from(Instant.now()), Integer.parseInt(occupancyField.getText()), extraField.getText());
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        } catch(Exception e) {
            listener.onFail();
        }
    }

    private void clearFields() {
        originField.getInput().clear();
        destinationField.getInput().clear();
        occupancyField.getInput().clear();
        pickupDateField.getInput().clear();
        extraField.getInput().clear();
    }

    private void clearErrors() {
        originField.clearError();
        destinationField.clearError();
        occupancyField.clearError();
        pickupDateField.clearError();
        extraField.clearError();
    }


    private void refreshClients() {
        clientField.setItems(UserBLL.indexClients().stream()
                .map(client -> new ComboItem(client.getEmail(), () -> {
                    selectedClient = client;
                }))
                .collect(Collectors.toList()));
    }


    @Override
    public void update() {
        List<User> clients = UserBLL.indexClients();
        if(clients.size() > 0) selectedClient = clients.get(0);

        refreshClients();

        if(clients.size() > 0) clientField.getSelectionModel().select(0);

    }
}

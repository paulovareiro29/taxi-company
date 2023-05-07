package pt.ipvc.layout.popup.paymenttype;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.bll.PaymentTypeBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.TextField;

public class CreatePaymentTypePopup extends Popup {

    private final TextField nameField;
    private final TextField descriptionField;

    public CreatePaymentTypePopup(EventListener listener) {
        super("New Payment Type", listener);

        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setIcon("tag--secondary.png");

        descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setIcon("message--secondary.png");

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
        addChildren(nameField, descriptionField, options);
    }

    private void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(nameField.getText().isBlank()) {
            nameField.setError("Name is required");
            hasError = true;
        }

        if  (hasError) return;

        try {
            PaymentTypeBLL.create(nameField.getText().trim(), descriptionField.getText().trim());
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        }catch(Exception e) {
            nameField.setError(e.getMessage());
            listener.onFail();
        }
    }

    private void clearFields() {
        nameField.getInput().clear();
        descriptionField.getInput().clear();
    }

    private void clearErrors() {
        nameField.clearError();
        descriptionField.clearError();
    }

    @Override
    public void update() {

    }
}

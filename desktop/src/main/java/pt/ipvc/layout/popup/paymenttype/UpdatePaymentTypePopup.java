package pt.ipvc.layout.popup.paymenttype;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.bll.PaymentTypeBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.PaymentType;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;
import pt.ipvc.layout.popup.DangerConfirmationPopup;

public class UpdatePaymentTypePopup extends Popup {

    private PaymentType type;
    private final TextField nameField;
    private final TextField descriptionField;

    public UpdatePaymentTypePopup(EventListener listener) {
        super("Update Payment Type", listener);

        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setIcon("tag--secondary.png");

        descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setIcon("message--secondary.png");

        Button cancelButton = new Button("Cancel", ButtonAppearance.outlined_primary);
        Button submitButton = new Button("Update");

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

        Button deleteButton = new Button("DELETE", ButtonAppearance.outlined_danger);
        deleteButton.setPrefWidth(Double.MAX_VALUE);
        deleteButton.setOnAction(e -> {
            DangerConfirmationPopup popup = new DangerConfirmationPopup(new EventListener() {
                @Override
                public void onSuccess() {
                    PaymentTypeBLL.remove(type.getId());
                    hide();
                    listener.onSuccess();
                    SceneHandler.updateScreen(ScreensEnum.PAYMENT_TYPES);
                }

                @Override
                public void onFail() {

                }

                @Override
                public void onCancel() {
                    System.out.println("Cancleed");
                }
            });
            popup.show(SceneHandler.stage);
        });

        options.getChildren().addAll(cancelButton, submitButton);
        addChildren(nameField, descriptionField, options, deleteButton);
    }

    private void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(nameField.getText().isBlank()) {
            nameField.setError("Name is required");
            hasError = true;
        }

        if  (hasError) return;

        type.setName(nameField.getText());
        type.setDescription(descriptionField.getText());

        try {
            PaymentTypeBLL.update(type);
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        }catch(Exception e) {
            nameField.setError(e.getMessage());
            listener.onFail();
        }
    }

    public void setPaymentType(PaymentType type){
        this.type = type;
        this.update();
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
        nameField.getInput().setText(type.getName());
        descriptionField.getInput().setText(type.getDescription());
    }

}

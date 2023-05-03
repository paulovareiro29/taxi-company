package pt.ipvc.layout.popup.brand;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Brand;

public class UpdateBrandPopup extends Popup {

    private Brand brand;
    private final TextField nameField;

    public UpdateBrandPopup(EventListener listener) {
        super("New Brand", listener);

        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setIcon("tag--secondary.png");

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

        options.getChildren().addAll(cancelButton, submitButton);
        addChildren(nameField, options);
    }

    private void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(nameField.getText().isBlank()) {
            nameField.setError("Name is required");
            hasError = true;
        }

        if  (hasError) return;

        brand.setName(nameField.getText());

        try {
            BrandBLL.update(brand);
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        }catch(Exception e) {
            nameField.setError(e.getMessage());
            listener.onFail();
        }
    }

    public void setBrand(Brand brand){
        this.brand = brand;
        this.update();
    }

    private void clearFields() {
        nameField.getInput().clear();
    }

    private void clearErrors() {
        nameField.clearError();
    }

    @Override
    public void update() {
        nameField.getInput().setText(brand.getName());
    }

}

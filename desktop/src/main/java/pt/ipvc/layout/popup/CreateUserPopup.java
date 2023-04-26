package pt.ipvc.layout.popup;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.utils.Validator;

public class CreateUserPopup extends Popup {

    private final TextField nameField;
    private final TextField emailField;
    private final TextField passwordField;

    public CreateUserPopup(EventListener listener) {
        super("New User", listener);

        nameField = new TextField();
        nameField.setPromptText("Name");

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new TextField();
        passwordField.setPromptText("Password");

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
        addChildren(nameField, emailField, passwordField, options);
    }

    private void handleSubmitButton() {
        clearErrors();

        if(nameField.getText().isBlank()) {
            nameField.setError("Name is required");
            return;
        }

        if(emailField.getText().isBlank()) {
            emailField.setError("Email is required");
            return;
        }

        if(passwordField.getText().isBlank()) {
            passwordField.setError("Password is required");
            return;
        }

        if(!Validator.validateEmail(emailField.getText())){
            emailField.setError("Invalid email format");
            return;
        }

        try {
            SessionBLL.register(nameField.getText().trim(), emailField.getText().trim(), "", passwordField.getText().trim());
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        } catch(Exception e) {
            emailField.setError(e.getMessage());
            listener.onFail();
        }
    }

    private void clearFields() {
        nameField.getInput().clear();
        emailField.getInput().clear();
        passwordField.getInput().clear();
    }

    private void clearErrors() {
        nameField.clearError();
        emailField.clearError();
        passwordField.clearError();
    }

    @Override
    public void update() {}
}

package pt.ipvc.layout.popup;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.Popup;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.TextField;

public class CreateUserPopup extends Popup {

    private TextField nameField;
    private TextField emailField;
    private TextField passwordField;

    public CreateUserPopup() {
        super("New User");

        nameField = new TextField();
        nameField.setPromptText("Name");

        emailField = new TextField();
        emailField.setPromptText("Email");

        passwordField = new TextField();
        passwordField.setPromptText("Password");

        Button cancelButton = new Button("Cancel", ButtonAppearance.outlined_primary);
        Button submitButton = new Button("Create");

        cancelButton.setOnAction(e -> hide());
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

    }
}

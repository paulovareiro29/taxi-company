package pt.ipvc.layout.popup.user;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.SessionBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Role;
import pt.ipvc.utils.StringUtils;
import pt.ipvc.utils.Validator;

import java.util.stream.Collectors;

public class CreateUserPopup extends Popup {

    private Role selectedRole;

    private final TextField nameField;
    private final TextField emailField;
    private final TextField passwordField;
    private final ComboBox roleField;

    public CreateUserPopup(EventListener listener) {
        super("New User", listener);

        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setIcon("user--secondary.png");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setIcon("email--secondary.png");

        passwordField = new TextField();
        passwordField.setPromptText("Password");
        passwordField.setIcon("lock--secondary.png");

        roleField = new ComboBox(RoleBLL.index().stream()
                .map(role -> new ComboItem(StringUtils.capitalize(role.getName()), () -> {
                    selectedRole = role;
                }))
                .collect(Collectors.toList()));
        roleField.setPrefWidth(Double.MAX_VALUE);
        roleField.setPromptText("Select role");
        roleField.setValue(roleField.getItems().stream().filter(item -> item.getLabel().equalsIgnoreCase(RoleBLL.getClientRole().getName())).findFirst().orElse(null));

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
        addChildren(nameField, emailField, roleField, passwordField, options);
    }

    private void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(nameField.getText().isBlank()) {
            nameField.setError("Name is required");
            hasError = true;
        }

        if(emailField.getText().isBlank()) {
            emailField.setError("Email is required");
            hasError = true;
        }else if(!Validator.validateEmail(emailField.getText())){
            emailField.setError("Invalid email format");
            hasError = true;
        }

        if(passwordField.getText().isBlank()) {
            passwordField.setError("Password is required");
            hasError = true;
        }

        if  (hasError) return;

        try {
            SessionBLL.register(nameField.getText().trim(), emailField.getText().trim(), "", passwordField.getText().trim(), selectedRole);
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
    public void update() {
        roleField.getItems().clear();
        roleField.getItems().setAll(RoleBLL.index().stream()
                        .filter(role -> !(SessionBLL.getAuthenticatedUser().getRole().getName().equalsIgnoreCase(RoleBLL.getSecretaryRole().getName())
                                && role.getName().equalsIgnoreCase(RoleBLL.getAdminRole().getName())))
                .map(role -> new ComboItem(StringUtils.capitalize(role.getName()), () -> {
                    selectedRole = role;
                }))
                .collect(Collectors.toList()));
        roleField.setValue(roleField.getItems().stream().filter(item -> item.getLabel().equalsIgnoreCase(RoleBLL.getClientRole().getName())).findFirst().orElse(null));
    }
}

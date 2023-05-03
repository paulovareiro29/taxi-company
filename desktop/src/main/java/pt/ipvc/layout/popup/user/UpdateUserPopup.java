package pt.ipvc.layout.popup.user;

import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.base.Scene;
import pt.ipvc.bll.RoleBLL;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Role;
import pt.ipvc.dal.User;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.DangerConfirmationPopup;
import pt.ipvc.utils.StringUtils;

import java.util.stream.Collectors;

public class UpdateUserPopup extends Popup {

    private User user;
    private Role selectedRole;

    private final TextField nameField;
    private final TextField phoneField;
    private final ComboBox roleField;

    private final TextField registrationNumberField;


    private final TextField addressField;
    private final TextField houseNumberField;
    private final TextField postalCodeField;
    private final TextField vatField;


    public UpdateUserPopup(EventListener listener) {
        super("Edit user", listener);

        /* INFORMATION */
        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setIcon("user--secondary.png");

        phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.setIcon("phone--secondary.png");

        registrationNumberField = new TextField();
        registrationNumberField.setIcon("card-details--secondary.png");
        registrationNumberField.setPromptText("Registration Number");

        roleField = new ComboBox(RoleBLL.index().stream()
                .map(role -> new ComboItem(StringUtils.capitalize(role.getName()), () -> {
                    selectedRole = role;
                    registrationNumberField.setVisible(selectedRole.getName().equals(RoleBLL.getDriverRole().getName()));
                }))
                .collect(Collectors.toList()));
        roleField.setPrefWidth(Double.MAX_VALUE);

        /* ADDITIONAL INFORMATION */
        addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setIcon("house--secondary.png");
        addressField.showOptional(true);
        HBox.setHgrow(addressField, Priority.ALWAYS);

        houseNumberField = new TextField();
        houseNumberField.setPromptText("Door");
        houseNumberField.setMaxWidth(100);

        HBox addressRow = new HBox(8);
        addressRow.getChildren().addAll(addressField, houseNumberField);

        postalCodeField = new TextField();
        postalCodeField.setPromptText("Postal code");
        postalCodeField.setIcon("marker--secondary.png");
        postalCodeField.showOptional(true);

        vatField = new TextField();
        vatField.setPromptText("VAT");
        vatField.setIcon("tax--secondary.png");
        vatField.showOptional(true);


        /* BUTTONS */
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

        Button deleteButton = new Button("DELETE", ButtonAppearance.outlined_danger);
        deleteButton.setPrefWidth(Double.MAX_VALUE);
        deleteButton.setOnAction(e -> {
            DangerConfirmationPopup popup = new DangerConfirmationPopup(new EventListener() {
                @Override
                public void onSuccess() {
                    UserBLL.remove(user.getId());
                    hide();
                    listener.onSuccess();
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

        addChildren(nameField, phoneField, roleField,registrationNumberField, new Separator(), addressRow, postalCodeField, vatField,  options, deleteButton);
    }

    public void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(nameField.getText().isBlank()) {
            nameField.setError("Name is required");
            hasError = true;
        }

        if(hasError) return;

        user.setName(nameField.getText());
        user.setRole(selectedRole);

        user.setPhone(phoneField.getText());
        user.setRegistrationNumber(registrationNumberField.getText());
        user.setAddress(addressField.getText());
        user.setHouseNumber(houseNumberField.getText());
        user.setPostalCode(postalCodeField.getText());
        user.setVAT(Integer.parseInt(vatField.getText().isBlank() ? "0" : vatField.getText()));


        try {
            UserBLL.update(user);
            listener.onSuccess();
            clearErrors();
            hide();
        } catch(Exception e) {
            listener.onFail();
        }
    }

    private void clearErrors() {
        nameField.clearError();
    }

    public void setUser(User user) {
        this.user = user;
        selectedRole = user.getRole();
        this.update();
    }

    @Override
    public void update() {
        this.setTitle(user.getEmail());
        nameField.getInput().setText(user.getName());
        phoneField.getInput().setText(user.getPhone());
        roleField.setValue(roleField.getItems().stream()
                .filter(item -> item.getLabel().equalsIgnoreCase(user.getRole().getName()))
                .findFirst().orElse(null));
        registrationNumberField.getInput().setText(user.getRegistrationNumber());
        registrationNumberField.setVisible(selectedRole.getName().equals(RoleBLL.getDriverRole().getName()));

        addressField.getInput().setText(user.getAddress());
        houseNumberField.getInput().setText(user.getHouseNumber());
        postalCodeField.getInput().setText(user.getPostalCode());
        Integer vat = user.getVAT();
        vatField.getInput().setText(vat == 0 ? "" : String.valueOf(vat));

    }
}

package pt.ipvc.components.inputs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class PasswordField extends VBox {

    private final HBox wrapper;
    private ImageView icon;
    private final javafx.scene.control.PasswordField input;
    private final Label errorLabel;

    public PasswordField(String text) {
        super(4);
        getStyleClass().add("text-field");

        wrapper = new HBox(12);
        input = new javafx.scene.control.PasswordField();
        errorLabel = new javafx.scene.control.Label();

        wrapper.getChildren().addAll(input);
        wrapper.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(input, Priority.ALWAYS);

        input.setText(text != null ? text : "");
        input.getStyleClass().add("text-field__input");
        wrapper.getStyleClass().add("text-field__wrapper");
        errorLabel.getStyleClass().add("text-field__error_label");
        errorLabel.managedProperty().bind(errorLabel.textProperty().isNotEmpty());
        errorLabel.visibleProperty().bind(errorLabel.textProperty().isNotEmpty());

        input.setOnKeyTyped(event -> {
            clearError();
        });

        this.getChildren().addAll(wrapper, errorLabel);
    }

    public PasswordField() {
        this("");
    }

    public String getText() {
        return input.getText();
    }

    public void setIcon(String icon) {
        if(icon == null) {
            wrapper.getChildren().remove(this.icon);
            wrapper.getStyleClass().remove("text-field__wrapper--icon");
            this.icon = null;
        }

        this.icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pt/ipvc/assets/icons/" + icon))));
        wrapper.getChildren().add(0, this.icon);
        wrapper.getStyleClass().add("text-field__wrapper--icon");
    }

    public void setPromptText(String text) {
        input.setPromptText(text);
    }

    public javafx.scene.control.TextField getInput() {
        return this.input;
    }

    public void setError(String message) {
        errorLabel.setText(message);
        getStyleClass().add("text-field--error");
    }

    public void clearError() {
        errorLabel.setText("");
        getStyleClass().remove("text-field--error");
    }
}

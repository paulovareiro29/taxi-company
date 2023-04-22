package pt.ipvc.components.inputs;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PasswordField extends VBox {

    private final javafx.scene.control.PasswordField input;
    private final Label label;

    public PasswordField(String text) {
        input = new javafx.scene.control.PasswordField();
        label = new javafx.scene.control.Label();

        input.setText(text != null ? text : null);
        input.getStyleClass().add("text-field");

        input.setOnKeyTyped(event -> {
            clearError();
        });

        this.getChildren().addAll(input, label);
    }

    public PasswordField() {
        this("");
    }

    public String getText() {
        return input.getText();
    }

    public void setPromptText(String text) {
        input.setPromptText(text);
    }

    public javafx.scene.control.TextField getInput() {
        return this.input;
    }

    public void setError(String message) {
        label.setText(message);
        label.setVisible(true);
        getStyleClass().add("text-field--error");
    }

    public void clearError() {
        label.setText("");
        label.setVisible(false);
        getStyleClass().remove("text-field--error");
    }
}

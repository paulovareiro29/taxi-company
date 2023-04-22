package pt.ipvc.components.inputs;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TextField extends VBox {

    private final javafx.scene.control.TextField input;
    private final Label errorLabel;

    public TextField(String text) {
        super(4);

        input = new javafx.scene.control.TextField(text != null ? text : "");
        errorLabel = new javafx.scene.control.Label();

        input.getStyleClass().add("text-field");
        errorLabel.getStyleClass().add("text-field__error_label");
        errorLabel.managedProperty().bind(errorLabel.textProperty().isNotEmpty());
        errorLabel.visibleProperty().bind(errorLabel.textProperty().isNotEmpty());

        input.setOnKeyTyped(event -> {
            clearError();
        });

        this.getChildren().addAll(input, errorLabel);
    }

    public TextField() {
        this("");
    }

    public String getText() {
        return input.getText();
    }

    public void setPromptText(String text) {
        input.setPromptText(text);
    }

    public void setError(String message) {
        errorLabel.setText(message);
        input.getStyleClass().add("text-field--error");
    }

    public void clearError() {
        errorLabel.setText("");
        input.getStyleClass().remove("text-field--error");
    }

    public javafx.scene.control.TextField getInput() {
        return this.input;
    }

}

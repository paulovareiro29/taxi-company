package pt.ipvc.components.inputs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pt.ipvc.components.Icon;

import java.util.Objects;

public class TextField extends VBox {

    private final HBox wrapper;
    private ImageView icon;
    private final javafx.scene.control.TextField input;
    private final Label errorLabel;
    private Boolean showOptional = false;
    private String promptText;

    public TextField(String text) {
        super(4);
        getStyleClass().add("text-field");

        wrapper = new HBox(12);
        input = new javafx.scene.control.TextField(text != null ? text : "");
        errorLabel = new javafx.scene.control.Label();

        wrapper.getChildren().addAll(input);
        wrapper.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(input, Priority.ALWAYS);

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

    public TextField() {
        this("");
    }

    public String getText() {
        return input.getText();
    }

    public void setText(String text) {
        input.setText(text != null ? text : "");
    }

    public void setIcon(String icon) {
        if(icon == null) {
            wrapper.getChildren().remove(this.icon);
            wrapper.getStyleClass().remove("text-field__wrapper--icon");
            this.icon = null;
        }

        this.icon = new Icon(icon);
        wrapper.getChildren().add(0, this.icon);
        wrapper.getStyleClass().add("text-field__wrapper--icon");
    }

    private void updatePromptText() {
        String prompt = promptText;
        if(this.showOptional)
            prompt = prompt + " (Optional)";

        input.setPromptText(prompt);
    }

    public void setPromptText(String text) {
        this.promptText = text;
        updatePromptText();
    }

    public void setError(String message) {
        errorLabel.setText(message);
        wrapper.getStyleClass().add("text-field__wrapper--error");
    }

    public void clearError() {
        errorLabel.setText("");
        wrapper.getStyleClass().remove("text-field__wrapper--error");
    }

    public void showOptional(Boolean show) {
        this.showOptional = show;
        updatePromptText();
    }

    public javafx.scene.control.TextField getInput() {
        return this.input;
    }

}

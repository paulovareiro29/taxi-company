package pt.ipvc.components.inputs;

public class PasswordField extends javafx.scene.control.PasswordField {
    public PasswordField(String text) {
        super();
        this.setText(text);
        this.getStyleClass().add("text-field");
    }

    public PasswordField() {
        this(null);
    }
}

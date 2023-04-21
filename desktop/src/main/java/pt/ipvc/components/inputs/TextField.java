package pt.ipvc.components.inputs;

public class TextField extends javafx.scene.control.TextField {
    public TextField(String text) {
        super(text);
        this.getStyleClass().add("text-field");
    }

    public TextField() {
        this(null);
    }

    public void setError() {
        getStyleClass().add("text-field--error");
    }

    public void clearError() {
        getStyleClass().remove("text-field--error");
    }
}

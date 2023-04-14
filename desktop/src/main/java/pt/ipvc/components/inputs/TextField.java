package pt.ipvc.components.inputs;

public class TextField extends javafx.scene.control.TextField {
    public TextField(String text) {
        super(text);
        this.getStyleClass().add("text-field");
    }

    public TextField() {
        this(null);
    }
}

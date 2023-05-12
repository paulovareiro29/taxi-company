package pt.ipvc.components;

public class Text extends javafx.scene.text.Text {

    public Text(String text) {
        super(text);
        setStyle("-fx-font-size: 16; -fx-fill: -fx-color-text-primary");
    }
}

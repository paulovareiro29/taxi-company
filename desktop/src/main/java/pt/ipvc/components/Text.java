package pt.ipvc.components;

public class Text extends javafx.scene.text.Text {

    private String color = "-fx-color-text-primary";
    private int size = 16;

    public Text(String text) {
        super(text);
        setColor(color);
    }

    public void setColor(String color) {
        this.color = color;
        setStyle("-fx-font-size: " + size + "; -fx-fill: " + color);
    }

    public void setSize(int size) {
        this.size = size;
        setStyle("-fx-font-size: " + size + "; -fx-fill: " + color);
    }
}

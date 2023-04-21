package pt.ipvc.components.buttons;

public class Button extends javafx.scene.control.Button {
    public Button(String label, ButtonAppearance appearance, ButtonSize size) {
        super(label);
        this.getStyleClass().add("button");
        this.getStyleClass().add("button--" + size.name().toLowerCase());
        this.getStyleClass().add("button--" + appearance.name().toLowerCase());
    }

    public Button(String label) {
        this(label, ButtonAppearance.primary, ButtonSize.NORMAL);
    }

    public Button(String label, ButtonAppearance appearance) {
        this(label, appearance, ButtonSize.NORMAL);
    }

    public Button(String label, ButtonSize size) {
        this(label, ButtonAppearance.primary, size);
    }
}

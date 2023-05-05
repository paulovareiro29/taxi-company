package pt.ipvc.components.card;

import javafx.scene.text.Text;

public class CardTitle extends Text {

    public CardTitle(String text) {
        super(text);
        getStyleClass().add("card-title");
    }
}

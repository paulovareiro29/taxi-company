package pt.ipvc.layout.screen;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SpaceBetweenRow extends HBox {

    public SpaceBetweenRow(Node firstColumn, Node secondColumn) {
        Region middleSpace = new Region();
        HBox.setHgrow(middleSpace, Priority.ALWAYS);

        getChildren().addAll( firstColumn, middleSpace, secondColumn);
    }
}
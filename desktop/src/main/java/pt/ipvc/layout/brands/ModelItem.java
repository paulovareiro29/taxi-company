package pt.ipvc.layout.brands;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import pt.ipvc.components.Text;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonIcon;
import pt.ipvc.dal.Model;

public class ModelItem extends HBox {

    public ModelItem(Model data) {
        super(8);
        setAlignment(Pos.CENTER_LEFT);

        Text name = new Text(data.getName());


        HBox options = new HBox(4);


        Button removeButton = new ButtonIcon("trash.png");
        removeButton.setStyle("-fx-background-color: -fx-color-danger");

        Button updateButton = new ButtonIcon("pencil.png");
        updateButton.setStyle("-fx-background-color: -fx-color-warning");

        options.getChildren().addAll(updateButton, removeButton);
        getChildren().addAll(name, options);
    }
}

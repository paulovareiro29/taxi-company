package pt.ipvc.layout.brands;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import pt.ipvc.base.EventListener;
import pt.ipvc.components.Text;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonIcon;
import pt.ipvc.dal.Model;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.model.UpdateModelPopup;

public class ModelItem extends HBox {

    public ModelItem(BrandItem parent, Model data) {
        super(8);
        setAlignment(Pos.CENTER_LEFT);

        Text name = new Text(data.getName());


        HBox options = new HBox(4);


        Button removeButton = new ButtonIcon("trash.png");
        removeButton.setStyle("-fx-background-color: -fx-color-danger");

        Button updateButton = new ButtonIcon("pencil.png");
        updateButton.setStyle("-fx-background-color: -fx-color-warning");

        /* POPUP */
        UpdateModelPopup updateModelPopup = new UpdateModelPopup(new EventListener() {
            @Override
            public void onSuccess() {
                parent.refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });
        updateModelPopup.setModel(data);
        updateButton.setOnAction(e -> updateModelPopup.show(SceneHandler.stage));


        options.getChildren().addAll(updateButton, removeButton);
        getChildren().addAll(name, options);
    }
}

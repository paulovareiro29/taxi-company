package pt.ipvc.components.inputs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import pt.ipvc.base.ComboItem;

import java.util.List;

public class ComboBox extends javafx.scene.control.ComboBox<ComboItem> {

    public ComboBox(List<ComboItem> items) {
        super(FXCollections.observableArrayList(items));
        getStyleClass().add("combobox");
        setOnAction(e -> getSelectionModel().getSelectedItem().onSelected());

        setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ComboItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getLabel());
                }
            }
        });
        setButtonCell(new ListCell<ComboItem>() {
            @Override
            protected void updateItem(ComboItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-text-fill: -fx-color-tertiary");
                    setGraphic(null);
                } else {
                    setText(item.getLabel());
                    setStyle("-fx-text-fill: -fx-color-text-primary");
                }
            }
        });

    }
}

package pt.ipvc.components.inputs;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import pt.ipvc.base.ComboItem;

public class ComboBox extends javafx.scene.control.ComboBox<ComboItem> {

    public ComboBox(ObservableList<ComboItem> items) {
        super(items);
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
                    setGraphic(null);
                } else {
                    setText(item.getLabel());
                }
            }
        });

    }
}

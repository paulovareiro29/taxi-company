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
        setOnAction(e -> {
            ComboItem selectedItem = getSelectionModel().getSelectedItem();
            if(selectedItem != null)
                selectedItem.onSelected();
        });

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
        setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ComboItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(getPromptText());
                    setStyle("-fx-text-fill: -fx-color-tertiary");
                    setGraphic(null);
                } else {
                    setText(item.getLabel());
                    setStyle("-fx-text-fill: -fx-color-text-primary");
                }
            }
        });

    }

    public void setItems(List<ComboItem> items) {
        super.setItems(FXCollections.observableArrayList(items));
    }

    public void reset(){
        getButtonCell().setText(null);
        getButtonCell().setItem(null);
        setValue(null);
    }
}

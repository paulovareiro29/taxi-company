package pt.ipvc.components.inputs;

import javafx.collections.FXCollections;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import pt.ipvc.base.ComboItem;

import java.util.List;
public class AutoCompleteComboBox extends SearchableComboBox<ComboItem> {


    public AutoCompleteComboBox(List<ComboItem> items) {
        super(FXCollections.observableArrayList(items));
        getStyleClass().add("searchable-combo-box");

        setOnAction(e -> {
            ComboItem selectedItem = getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                setValue(selectedItem);
                selectedItem.onSelected();
            }
        });

        setConverter(new StringConverter<>() {
            @Override
            public String toString(ComboItem item) {
                return item.getLabel();
            }

            @Override
            public ComboItem fromString(String string) {
                return null;
            }
        });
    }

    public void setItems(List<ComboItem> items) {
        super.setItems(FXCollections.observableArrayList(items));
    }
}
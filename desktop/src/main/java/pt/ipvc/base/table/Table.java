package pt.ipvc.base.table;

import javafx.scene.control.TableView;
import pt.ipvc.base.UIComponent;

public class Table<T> extends TableView<T> implements UIComponent {

    public Table() {
        setPrefHeight(0);
        setFixedCellSize(50);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @Override
    public void update() {
        double rowCount = getItems().size();
        double rowHeight = getFixedCellSize();
        double prefHeight = rowCount * rowHeight;
        setPrefHeight(prefHeight + 50);
    }
}

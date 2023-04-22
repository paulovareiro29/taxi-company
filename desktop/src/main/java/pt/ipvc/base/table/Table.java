package pt.ipvc.base.table;

import javafx.scene.control.TableView;
import pt.ipvc.base.UIComponent;

import java.util.List;
import java.util.function.Predicate;

public abstract class Table<T> extends TableView<T> implements UIComponent {

    protected Predicate<T> filters;

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

    public abstract void refresh();

    protected void updateData(List<T> data) {
        getItems().clear();
        getItems().setAll(data);
    }

    protected void addOrFilter(Predicate<T> filter) {
        filters = filters == null ? filter : filters.or(filter);
    }

    protected void addAndFilter(Predicate<T> filter) {
        filters = filters == null ? filter : filters.and(filter);
    }
}

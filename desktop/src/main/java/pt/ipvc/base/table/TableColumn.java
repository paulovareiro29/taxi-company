package pt.ipvc.base.table;

import pt.ipvc.base.UIComponent;

public class TableColumn<S, T> extends javafx.scene.control.TableColumn<S,T> implements UIComponent {

    public TableColumn(String title) {
        super(title);
    }

    @Override
    public void update() {

    }

}

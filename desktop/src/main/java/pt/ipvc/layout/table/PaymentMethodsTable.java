package pt.ipvc.layout.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.table.ButtonIconTableCell;
import pt.ipvc.base.table.Table;
import pt.ipvc.base.table.TableColumn;
import pt.ipvc.bll.PaymentMethodBLL;
import pt.ipvc.dal.PaymentMethod;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.paymenttype.UpdatePaymentTypePopup;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMethodsTable extends Table<PaymentMethod> {

    private String nameFilter;
    private final UpdatePaymentTypePopup editPopup;

    public PaymentMethodsTable() {
        editPopup = new UpdatePaymentTypePopup(new EventListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });

        TableColumn<PaymentMethod, String> nameColumn = new TableColumn<>("Name");
        TableColumn<PaymentMethod, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<PaymentMethod, String> settingsColumn = new TableColumn<>("");

        settingsColumn.setPrefWidth(64);
        settingsColumn.setMinWidth(64);
        settingsColumn.setMaxWidth(64);

        getColumns().addAll(nameColumn, descriptionColumn, settingsColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        settingsColumn.setCellValueFactory(data -> new SimpleStringProperty(""));
        settingsColumn.setCellFactory(data -> {
            ButtonIconTableCell<PaymentMethod> cell = new ButtonIconTableCell<>("settings.png");
            cell.setOnClick(event -> {
                editPopup.setPaymentType(cell.getTableView().getItems().get(cell.getIndex()));
                editPopup.show(SceneHandler.stage);
            });
            return cell;
        });

        addOrFilter(t -> t.getName().toLowerCase().contains(nameFilter != null ? nameFilter : ""));

        refresh();
    }

    @Override
    public void refresh() {
        List<PaymentMethod> types = PaymentMethodBLL.index().stream()
                .filter(filters)
                .collect(Collectors.toList());

        updateData(types);
        update();
    }

    public void update() {
        super.update();
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public void clearFilters() {
        nameFilter = null;
    }
}

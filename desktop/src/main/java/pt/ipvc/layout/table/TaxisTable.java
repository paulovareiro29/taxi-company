package pt.ipvc.layout.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.table.ButtonIconTableCell;
import pt.ipvc.base.table.Table;
import pt.ipvc.base.table.TableColumn;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.bll.UserBLL;
import pt.ipvc.dal.Taxi;
import pt.ipvc.dal.User;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.taxi.UpdateTaxiPopup;
import pt.ipvc.layout.popup.user.UpdateUserPopup;

import java.util.List;
import java.util.stream.Collectors;

public class TaxisTable extends Table<Taxi> {

    private String plateFilter;
    private final UpdateTaxiPopup editPopup;

    public TaxisTable() {
        editPopup = new UpdateTaxiPopup(new EventListener() {
            @Override
            public void onSuccess() {
                refresh();
            }

            @Override
            public void onFail() {}

            @Override
            public void onCancel() {}
        });

        TableColumn<Taxi, String> plateColumn = new TableColumn<>("License Plate");
        TableColumn<Taxi, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Taxi, String> occupancyColumn = new TableColumn<>("Maximum occupancy");
        TableColumn<Taxi, String> yearColumn = new TableColumn<>("Year");
        TableColumn<Taxi, String> settingsColumn = new TableColumn<>("");
        settingsColumn.setPrefWidth(64);
        settingsColumn.setMinWidth(64);
        settingsColumn.setMaxWidth(64);

        getColumns().addAll(plateColumn, modelColumn, occupancyColumn, yearColumn, settingsColumn);

        plateColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        modelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModel().getBrand().getName() + " - " + data.getValue().getModel().getName()));
        occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        settingsColumn.setCellValueFactory(data -> new SimpleStringProperty(""));
        settingsColumn.setCellFactory(data -> {
            ButtonIconTableCell<Taxi> cell = new ButtonIconTableCell<>("settings.png");
            cell.setOnClick(event -> {
                editPopup.setTaxi(cell.getTableView().getItems().get(cell.getIndex()));
                editPopup.show(SceneHandler.stage);
            });
            return cell;
        });

        addOrFilter(t -> t.getLicensePlate().toLowerCase().contains(plateFilter != null ? plateFilter : ""));

        refresh();
    }

    @Override
    public void refresh() {
        List<Taxi> taxis = TaxiBLL.index().stream()
                .filter(filters)
                .collect(Collectors.toList());

        updateData(taxis);
        update();
    }

    public void update() {
        super.update();
    }

    public void setPlateFilter(String plate) {
        plateFilter = plate;
    }

    public void clearFilters() {
        plateFilter = null;
    }
}

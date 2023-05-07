package pt.ipvc.layout.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipvc.base.table.ButtonIconTableCell;
import pt.ipvc.base.table.Table;
import pt.ipvc.base.table.TableColumn;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.dal.Booking;
import pt.ipvc.utils.DateUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BookingsTable extends Table<Booking> {

    public BookingsTable() {

        TableColumn<Booking, String> originColumn = new TableColumn<>("Origin");
        TableColumn<Booking, String> destinationColumn = new TableColumn<>("Destination");
        TableColumn<Booking, String> clientColumn = new TableColumn<>("Client");
        TableColumn<Booking, String> dateColumn = new TableColumn<>("Date");
        TableColumn<Booking, String> statusColumn = new TableColumn<>("Status");
        TableColumn<Booking, String> settingsColumn = new TableColumn<>("");

        statusColumn.setPrefWidth(100);
        statusColumn.setMinWidth(100);
        statusColumn.setMaxWidth(100);

        settingsColumn.setPrefWidth(64);
        settingsColumn.setMinWidth(64);
        settingsColumn.setMaxWidth(64);

        getColumns().addAll(originColumn, destinationColumn, clientColumn, statusColumn, dateColumn, settingsColumn);

        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        clientColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClient().getEmail()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(DateUtils.format(data.getValue().getPickupDate())));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getState().getName()));
        settingsColumn.setCellValueFactory(data -> new SimpleStringProperty(""));
        settingsColumn.setCellFactory(data -> {
            ButtonIconTableCell<Booking> cell = new ButtonIconTableCell<>("settings.png");
            cell.setOnClick(event -> {
                System.out.println("TODO: VIEW BOOKING");
            });
            return cell;
        });

        //addOrFilter(t -> t.getLicensePlate().toLowerCase().contains(plateFilter != null ? plateFilter : ""));

        refresh();
    }

    @Override
    public void refresh() {
        List<Booking> bookings = BookingBLL.index()/*.stream()
                .filter(filters)
                .collect(Collectors.toList())*/;

        updateData(bookings);
        update();
    }

    public void update() {
        super.update();
    }

    public void clearFilters() {

    }
}

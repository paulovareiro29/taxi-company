package pt.ipvc.layout.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.table.ButtonIconTableCell;
import pt.ipvc.base.table.Table;
import pt.ipvc.bll.FeedbackBLL;
import pt.ipvc.dal.Feedback;
import pt.ipvc.base.table.TableColumn;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.layout.popup.user.UpdateUserPopup;

import java.util.List;
import java.util.stream.Collectors;

public class FeedBacksTable extends Table<Feedback> {


    public FeedBacksTable() {

        TableColumn<Feedback, String> reviewColumn = new TableColumn<>("Review");
        TableColumn<Feedback, Integer> ratingColumn = new TableColumn<>("Rating");
        TableColumn<Feedback, String> settingsColumn = new TableColumn<>("");
        settingsColumn.setPrefWidth(64);
        settingsColumn.setMinWidth(64);
        settingsColumn.setMaxWidth(64);

        getColumns().addAll(reviewColumn, ratingColumn, settingsColumn);

        reviewColumn.setCellValueFactory(new PropertyValueFactory<>("Review"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        settingsColumn.setCellValueFactory(data -> new SimpleStringProperty(""));


        refresh();
    }

    @Override
    public void refresh() {
        List<Feedback> feedbacks = FeedbackBLL.index().stream()
                .collect(Collectors.toList());

        updateData(feedbacks);
        update();
    }

    public void update() {
        super.update();
    }


}

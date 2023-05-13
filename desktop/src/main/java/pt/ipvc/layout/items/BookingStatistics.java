package pt.ipvc.layout.items;

import javafx.scene.layout.VBox;
import pt.ipvc.base.UIComponent;
import pt.ipvc.bll.BookingBLL;
import pt.ipvc.bll.PaymentBLL;
import pt.ipvc.components.SmallLabel;
import pt.ipvc.components.Text;
import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Payment;
import pt.ipvc.layout.screen.SpaceBetweenRow;
import pt.ipvc.utils.StringUtils;

import java.util.HashMap;

public class BookingStatistics extends Card implements UIComponent {

    private final VBox historyContainer;
    private final Text pendingCount;
    private final Text confirmedCount;
    private final Text ongoingCount;
    private final Text completedCount;
    private final Text cancelledCount;

    private final HashMap<String, String> colorMap = new HashMap<>() {
        {
            put("pending", "-fx-color-warning");
            put("confirmed", "-fx-color-text-primary");
            put("ongoing", "-fx-color-info");
            put("completed", "-fx-color-success");
            put("cancelled", "-fx-color-danger");
        }
    };

    public BookingStatistics() {
        setSpacing(12);

        Text title = new Text("Booking statistics");
        title.setSize(20);

        SmallLabel countLabel = new SmallLabel("Bookings by status");

        pendingCount = new Text("0");
        pendingCount.setColor(colorMap.get("pending"));

        confirmedCount = new Text("0");
        confirmedCount.setColor(colorMap.get("confirmed"));

        ongoingCount = new Text("0");
        ongoingCount.setColor(colorMap.get("ongoing"));

        completedCount = new Text("0");
        completedCount.setColor(colorMap.get("completed"));

        cancelledCount = new Text("0");
        cancelledCount.setColor(colorMap.get("cancelled"));

        VBox countBox = new VBox();
        countBox.getChildren()
                .addAll(countLabel, new SpaceBetweenRow(new Text("Pending"), pendingCount),
                                    new SpaceBetweenRow(new Text("Confirmed"), confirmedCount),
                                    new SpaceBetweenRow(new Text("On Going"), ongoingCount),
                                    new SpaceBetweenRow(new Text("Completed"), completedCount),
                                    new SpaceBetweenRow(new Text("Cancelled"),cancelledCount));

        SmallLabel historyLabel = new SmallLabel("Last 10 bookings");
        historyContainer = new VBox();

        VBox box = new VBox();
        box.getChildren().addAll(historyLabel, historyContainer);

        getChildren().addAll(title, countBox, box);
    }

    @Override
    public void update() {
        pendingCount.setText("" + BookingBLL.countByState("pending"));
        confirmedCount.setText("" + BookingBLL.countByState("confirmed"));
        ongoingCount.setText("" + BookingBLL.countByState("ongoing"));
        completedCount.setText("" + BookingBLL.countByState("completed"));
        cancelledCount.setText("" + BookingBLL.countByState("cancelled"));

        historyContainer.getChildren().clear();
        for(Booking booking : BookingBLL.getLast10()) {
            Text status = new Text(StringUtils.capitalize(booking.getState().getName()));
            status.setColor(colorMap.get(booking.getState().getName().toLowerCase()));

            Text route = new Text(booking.getOrigin() + " -> " + booking.getDestination());

            historyContainer.getChildren().add(new SpaceBetweenRow(route, status));
        }
    }
}

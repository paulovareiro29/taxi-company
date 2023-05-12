package pt.ipvc.views.screens;

import javafx.scene.layout.VBox;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.BookingStateBLL;
import pt.ipvc.bll.PaymentBLL;
import pt.ipvc.bll.TripBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.components.ScrollPane;
import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Payment;
import pt.ipvc.dal.Trip;
import pt.ipvc.layout.items.InfoBookingItem;
import pt.ipvc.layout.items.InfoPaymentItem;
import pt.ipvc.layout.items.InfoTripItem;
import pt.ipvc.layout.items.PendingBookingItem;

import java.util.UUID;

public class ViewBookingScreen extends Screen {

    public static Booking booking;

    private VBox container;

    public ViewBookingScreen() {
        Heading title = new Heading("Booking info");

        container = new VBox(8);
        ScrollPane scrollPane = new ScrollPane();
        container.setFillWidth(true);
        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);

        getChildren().addAll(title, scrollPane);
    }

    @Override
    public void update() {
        container.getChildren().clear();

        UUID state = booking.getState().getId();
        UUID pending = BookingStateBLL.getByName("pending").getId();
        UUID completed = BookingStateBLL.getByName("completed").getId();

        if (state.equals(pending)) {
            container.getChildren().add(new PendingBookingItem(booking));
        }else if(state.equals(completed)) {
            Trip trip = TripBLL.getByBooking(booking);
            Payment payment = PaymentBLL.getByTrip(trip);

            container.getChildren().addAll(new InfoBookingItem(booking), new Heading("Trip"), new InfoTripItem(trip), new Heading("Payment info"), new InfoPaymentItem(payment));
        }else{
            container.getChildren().add(new InfoBookingItem(booking));
        }
    }
}

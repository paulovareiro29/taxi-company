package pt.ipvc.views.screens;

import javafx.scene.layout.VBox;
import pt.ipvc.base.Screen;
import pt.ipvc.bll.BookingStateBLL;
import pt.ipvc.bll.TripBLL;
import pt.ipvc.components.Heading;
import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Trip;
import pt.ipvc.layout.items.InfoBookingItem;
import pt.ipvc.layout.items.InfoTripItem;
import pt.ipvc.layout.items.PendingBookingItem;

import java.util.UUID;

public class ViewBookingScreen extends Screen {

    public static Booking booking;

    private VBox container;

    public ViewBookingScreen() {
        Heading title = new Heading("Booking info");

        container = new VBox(8);

        getChildren().addAll(title, container);
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
            container.getChildren().addAll(new InfoBookingItem(booking), new InfoTripItem(trip));
        }else{
            container.getChildren().add(new InfoBookingItem(booking));
        }
    }
}

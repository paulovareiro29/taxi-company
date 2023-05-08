package pt.ipvc.layout.items;

import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Booking;

public class CompletedBookingItem extends Card {

    public final Booking data;

    public CompletedBookingItem(Booking data) {
        this.data = data;
        setSpacing(8);


        getChildren().addAll();
    }


}

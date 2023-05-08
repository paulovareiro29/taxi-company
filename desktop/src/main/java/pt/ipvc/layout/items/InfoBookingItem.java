package pt.ipvc.layout.items;

import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Booking;

public class InfoBookingItem extends Card {

    public final Booking data;

    public InfoBookingItem(Booking data) {
        this.data = data;
        setSpacing(8);


        getChildren().addAll();
    }


}

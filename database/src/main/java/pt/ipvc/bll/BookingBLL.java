package pt.ipvc.bll;

import pt.ipvc.dal.Booking;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;

public class BookingBLL {
    
    public static List<Booking> index() {
        return Database.query("booking.index").getResultList();
    }

    public static Booking get(UUID id) {
        return Database.find(Booking.class, id);
    }

    public static void create(Booking entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Booking entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Booking entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("Booking.count").getSingleResult()).intValue();
    }

}


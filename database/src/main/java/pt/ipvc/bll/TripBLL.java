package pt.ipvc.bll;

import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Trip;
import pt.ipvc.dal.User;
import pt.ipvc.database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class TripBLL {

    public static List<Trip> index() {
        return Database.query("trip.index").getResultList();
    }

    public static Trip get(UUID id) {
        return Database.find(Trip.class, id);
    }

    public static Trip getByBooking(Booking booking) {
        return (Trip) Database.query("trip.get_by_booking")
                .setParameter("id", booking.getId())
                .getResultStream().findFirst().orElse(null);
    }

    public static void create(Booking booking, User employee, Date pickup, Date dropoff, float price) {
        Trip entity = new Trip();
        entity.setBooking(booking);
        entity.setEmployee(employee);
        entity.setPickupDate(pickup);
        entity.setDropoffDate(dropoff);
        entity.setPrice(price);

        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Trip entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Trip entity = get(id);
        entity.setDeletedAt(Timestamp.from(Instant.now()));

        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("trip.count").getSingleResult()).intValue();
    }

}

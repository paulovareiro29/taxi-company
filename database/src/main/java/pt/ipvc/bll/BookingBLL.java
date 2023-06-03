package pt.ipvc.bll;

import pt.ipvc.dal.Booking;
import pt.ipvc.dal.Payment;
import pt.ipvc.dal.User;
import pt.ipvc.database.Database;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BookingBLL {
    
    public static List<Booking> index() {
        return Database.query("booking.index").getResultList();
    }

    public static Booking get(UUID id) {
        return Database.find(Booking.class, id);
    }

    public static void create(User client, String origin, String destination, Date pickupDate, int occupancy, String extra) {
        Booking entity = new Booking();
        entity.setClient(client);
        entity.setOrigin(origin);
        entity.setDestination(destination);
        entity.setPickupDate(pickupDate);
        entity.setOccupancy(occupancy);
        entity.setExtra(extra);
        entity.setBookedBy(SessionBLL.getAuthenticatedUser());
        entity.setState(BookingStateBLL.getByName("pending"));

        try {
            Database.beginTransaction();
            Database.insert(entity);
            Database.commitTransaction();
        } catch (Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static void update(Booking entity) {
        try {
            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        } catch (Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static void remove(UUID id) {
        Booking entity = get(id);
        entity.setDeletedAt(Timestamp.from(Instant.now()));

        try {
            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        } catch (Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static int count() {
        return ((Long) Database.query("booking.count").getSingleResult()).intValue();
    }

    public static List<Booking> getLast10() {
        return (List<Booking>) Database.query("booking.index").setMaxResults(10).getResultList();
    }

    public static int countByState(String state) {
        return ((Long) Database.query("booking.count_state").setParameter("state", state).getSingleResult()).intValue();
    }
}


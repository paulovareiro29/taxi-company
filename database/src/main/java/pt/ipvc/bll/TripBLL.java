package pt.ipvc.bll;

import pt.ipvc.dal.Trip;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;


public class TripBLL {

    public static List<Trip> index() {
        return Database.query("trip.index").getResultList();
    }

    public static Trip get(UUID id) {
        return Database.find(Trip.class, id);
    }

    public static void create(Trip entity) {
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

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("trip.count").getSingleResult()).intValue();
    }

}

package pt.ipvc.bll;

import pt.ipvc.dal.Trip;
import pt.ipvc.database.Database;

import java.util.List;


public class TripBLL {

    public static List<Trip> index() {
        return Database.query("trip.index").getResultList();
    }

    public static Trip get(Long id) {
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

    public static void remove(Long id) {
        Trip entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("trip.count").getSingleResult()).intValue();
    }

}

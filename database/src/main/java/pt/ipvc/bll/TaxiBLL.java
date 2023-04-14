package pt.ipvc.bll;

import pt.ipvc.dal.Taxi;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;


public class TaxiBLL {

    public static List<Taxi> index() {
        return Database.query("taxi.index").getResultList();
    }

    public static Taxi get(UUID id) {
        return Database.find(Taxi.class, id);
    }

    public static void create(Taxi entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Taxi entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Taxi entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("taxi.count").getSingleResult()).intValue();
    }

}

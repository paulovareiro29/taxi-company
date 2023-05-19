package pt.ipvc.bll;

import pt.ipvc.dal.Model;
import pt.ipvc.dal.Taxi;
import pt.ipvc.dal.User;
import pt.ipvc.database.Database;
import pt.ipvc.exceptions.PlateAlreadyExistsException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


public class TaxiBLL {

    public static List<Taxi> index() {
        return Database.query("taxi.index").getResultList();
    }

    public static Taxi get(UUID id) {
        return Database.find(Taxi.class, id);
    }

    public static Taxi getByPlate(String plate) {
        return (Taxi) Database.query("taxi.get_by_plate")
                .setParameter("plate", plate)
                .getResultStream().findFirst().orElse(null);
    }

    public static void create(String plate, Model model, int maxOccupancy, int year, String color) throws PlateAlreadyExistsException {
        if(getByPlate(plate) != null)
            throw new PlateAlreadyExistsException();

        Taxi entity = new Taxi();
        entity.setLicensePlate(plate);
        entity.setModel(model);
        entity.setMaxOccupancy(maxOccupancy);
        entity.setYear(year);
        entity.setColor(color);

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
        entity.setDeletedAt(Timestamp.from(Instant.now()));

        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("taxi.count").getSingleResult()).intValue();
    }

}

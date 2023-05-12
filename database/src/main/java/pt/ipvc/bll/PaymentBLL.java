package pt.ipvc.bll;

import pt.ipvc.dal.Payment;
import pt.ipvc.dal.Trip;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;


public class PaymentBLL {

    public static List<Payment> index() {
        return Database.query("payment.index").getResultList();
    }

    public static Payment get(UUID id) {
        return Database.find(Payment.class, id);
    }

    public static Payment getByTrip(Trip trip) {
        return (Payment) Database.query("payment.get_by_trip")
                .setParameter("id", trip.getId())
                .getResultStream().findFirst().orElse(null);
    }

    public static void create(Payment entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Payment entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Payment entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("payment.count").getSingleResult()).intValue();
    }

}

package pt.ipvc.bll;

import pt.ipvc.dal.Payment;
import pt.ipvc.database.Database;

import java.util.List;


public class PaymentBLL {

    public static List<Payment> index() {
        return Database.query("payment.index").getResultList();
    }

    public static Payment get(Long id) {
        return Database.find(Payment.class, id);
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

    public static void remove(Long id) {
        Payment entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("payment.count").getSingleResult()).intValue();
    }

}

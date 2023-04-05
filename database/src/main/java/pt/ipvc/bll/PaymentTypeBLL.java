package pt.ipvc.bll;

import pt.ipvc.dal.PaymentType;
import pt.ipvc.database.Database;

import java.util.List;


public class PaymentTypeBLL {

    public static List<PaymentType> index() {
        return Database.query("payment_type.index").getResultList();
    }

    public static PaymentType get(Long id) {
        return Database.find(PaymentType.class, id);
    }

    public static void create(PaymentType entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(PaymentType entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(Long id) {
        PaymentType entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("payment_type.count").getSingleResult()).intValue();
    }

    public static PaymentType getByName(String name) {
        return (PaymentType) Database.query("payment_type.get_by_name")
                .setParameter("name", name)
                .getSingleResult();
    }
}

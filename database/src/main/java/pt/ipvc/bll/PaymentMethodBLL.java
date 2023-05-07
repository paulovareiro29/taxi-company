package pt.ipvc.bll;

import pt.ipvc.dal.PaymentMethod;
import pt.ipvc.database.Database;
import pt.ipvc.exceptions.NameAlreadyExistsException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


public class PaymentMethodBLL {

    public static List<PaymentMethod> index() {
        return Database.query("payment_method.index").getResultList();
    }

    public static PaymentMethod get(UUID id) {
        return Database.find(PaymentMethod.class, id);
    }

    public static void create(String name, String description) throws NameAlreadyExistsException{
        if(getByName(name) != null)
            throw new NameAlreadyExistsException();

        PaymentMethod entity = new PaymentMethod();
        entity.setName(name);
        entity.setDescription(description);

        try {
            Database.beginTransaction();
            Database.insert(entity);
            Database.commitTransaction();
        }catch(Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static void update(PaymentMethod entity) {
        try {
            Database.beginTransaction();
            Database.insert(entity);
            Database.commitTransaction();
        }catch(Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static void remove(UUID id) {
        PaymentMethod entity = get(id);
        entity.setDeletedAt(Timestamp.from(Instant.now()));

        try {
            Database.beginTransaction();
            Database.update(entity);
            Database.commitTransaction();
        }catch(Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static int count() {
        return ((Long) Database.query("payment_method.count").getSingleResult()).intValue();
    }

    public static PaymentMethod getByName(String name) {
        return (PaymentMethod) Database.query("payment_method.get_by_name")
                .setParameter("name", name)
                .getResultStream().findFirst().orElse(null);
    }
}

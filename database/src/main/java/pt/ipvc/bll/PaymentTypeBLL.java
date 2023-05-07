package pt.ipvc.bll;

import pt.ipvc.dal.PaymentType;
import pt.ipvc.database.Database;
import pt.ipvc.exceptions.EmailAlreadyInUseException;
import pt.ipvc.exceptions.NameAlreadyExistsException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;


public class PaymentTypeBLL {

    public static List<PaymentType> index() {
        return Database.query("payment_type.index").getResultList();
    }

    public static PaymentType get(UUID id) {
        return Database.find(PaymentType.class, id);
    }

    public static void create(String name, String description) throws NameAlreadyExistsException{
        if(getByName(name) != null)
            throw new NameAlreadyExistsException();

        PaymentType entity = new PaymentType();
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

    public static void update(PaymentType entity) {
        try {
            Database.beginTransaction();
            Database.insert(entity);
            Database.commitTransaction();
        }catch(Exception e) {
            Database.rollbackTransaction();
        }
    }

    public static void remove(UUID id) {
        PaymentType entity = get(id);
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
        return ((Long) Database.query("payment_type.count").getSingleResult()).intValue();
    }

    public static PaymentType getByName(String name) {
        return (PaymentType) Database.query("payment_type.get_by_name")
                .setParameter("name", name)
                .getResultStream().findFirst().orElse(null);
    }
}

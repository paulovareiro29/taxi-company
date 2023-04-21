package pt.ipvc.bll;

import pt.ipvc.dal.User;
import pt.ipvc.database.Database;
import pt.ipvc.exceptions.EmailAlreadyInUseException;

import java.util.List;
import java.util.UUID;


public class UserBLL {

    public static List<User> index() {
        return Database.query("user.index").getResultList();
    }

    public static User get(UUID id) {
        return Database.find(User.class, id);
    }

    public static User getByEmail(String email) {
        return (User) Database.query("user.get_by_email")
                .setParameter("email", email)
                .getResultStream().findFirst().orElse(null);
    }

    public static void create(User entity) throws EmailAlreadyInUseException {
        if(getByEmail(entity.getEmail()) != null)
            throw new EmailAlreadyInUseException();

        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(User entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        User entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("user.count").getSingleResult()).intValue();
    }
}

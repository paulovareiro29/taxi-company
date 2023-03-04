package pt.ipvc.bll;

import pt.ipvc.dal.User;
import pt.ipvc.database.Database;

import java.util.List;


public class UserBLL {

    public static List<User> index() {
        return Database.query("user.index").getResultList();
    }

    public static User get(Long id) {
        return Database.find(User.class, id);
    }

    public static void create(User entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(User entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(Long id) {
        User entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("user.count").getSingleResult()).intValue();
    }
}

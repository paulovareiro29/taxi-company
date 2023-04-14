package pt.ipvc.bll;

import pt.ipvc.dal.Role;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;


public class RoleBLL {

    public static List<Role> index() {
        return Database.query("role.index").getResultList();
    }

    public static Role get(UUID id) {
        return Database.find(Role.class, id);
    }

    public static void create(Role entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Role entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Role entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("role.count").getSingleResult()).intValue();
    }

    public static Role getByName(String name) {
        return (Role) Database.query("role.get_by_name")
                .setParameter("name", name)
                .getSingleResult();
    }

    public static Role getClientRole() {
        return getByName("client");
    }

    public static Role getAdminRole() {
        return getByName("administration");
    }

    public static Role getSecretaryRole() {
        return getByName("secretary");
    }
}

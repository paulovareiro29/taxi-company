package pt.ipvc.bll;

import pt.ipvc.dal.Client;
import pt.ipvc.database.Database;

import java.util.List;


public class ClientBLL {

    public static List<Client> index() {
        return Database.query("client.index").getResultList();
    }

    public static Client get(Long id) {
        return Database.find(Client.class, id);
    }

    public static void create(Client entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Client entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(Long id) {
        Client entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("client.count").getSingleResult()).intValue();
    }
}

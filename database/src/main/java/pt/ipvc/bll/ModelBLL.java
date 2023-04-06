package pt.ipvc.bll;

import pt.ipvc.dal.Model;
import pt.ipvc.database.Database;

import java.util.List;


public class ModelBLL {

    public static List<Model> index() {
        return Database.query("model.index").getResultList();
    }

    public static Model get(Long id) {
        return Database.find(Model.class, id);
    }

    public static void create(Model entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Model entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(Long id) {
        Model entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("model.count").getSingleResult()).intValue();
    }

    public static Model getByName(String name) {
        return (Model) Database.query("model.get_by_name")
                .setParameter("name", name)
                .getSingleResult();
    }

}
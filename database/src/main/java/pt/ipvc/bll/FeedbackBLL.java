package pt.ipvc.bll;

import pt.ipvc.dal.Feedback;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;


public class FeedbackBLL {

    public static List<Feedback> index() {
        return Database.query("feedback.index").getResultList();
    }

    public static Feedback get(UUID id) {
        return Database.find(Feedback.class, id);
    }

    public static void create(Feedback entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Feedback entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Feedback entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("feedback.count").getSingleResult()).intValue();
    }

}

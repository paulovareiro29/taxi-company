package pt.ipvc.bll;

import pt.ipvc.dal.Feedback;
import pt.ipvc.dal.Trip;
import pt.ipvc.dal.User;
import pt.ipvc.database.Database;
import pt.ipvc.exceptions.FeedbackAlreadyExistsException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class FeedbackBLL {

    public static List<Feedback> index() {
        return Database.query("feedback.index").getResultList();
    }

    public static Feedback get(UUID id) {
        return Database.find(Feedback.class, id);
    }

    public static void create(Trip trip, User client, int rating, String review) throws FeedbackAlreadyExistsException {

        if(FeedbackBLL.getByTrip(trip) != null)
            throw new FeedbackAlreadyExistsException();

        Feedback entity = new Feedback();
        entity.setTrip(trip);
        entity.setClient(client);
        entity.setRating(rating);
        entity.setReview(review);

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

    public static Feedback getByTrip(Trip trip) {
        return (Feedback) Database.query("feedback.get_by_trip")
                .setParameter("id", trip.getId())
                .getResultStream().findFirst().orElse(null);
    }

    public static List<Feedback> getByClient(User client) {
        return (List<Feedback>) Database.query("feedback.get_by_client")
                .setParameter("id", client.getId())
                .getResultStream().collect(Collectors.toList());
    }

    public static List<Feedback> getByDriver(User driver) {
        return (List<Feedback>) Database.query("feedback.get_by_driver")
                .setParameter("id", driver.getId())
                .getResultStream().collect(Collectors.toList());
    }
}

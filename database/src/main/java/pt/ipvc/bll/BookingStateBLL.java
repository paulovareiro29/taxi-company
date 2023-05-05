package pt.ipvc.bll;

import pt.ipvc.dal.BookingState;
import pt.ipvc.database.Database;

import java.util.List;
import java.util.UUID;

public class BookingStateBLL {
    
    public static List<BookingState> index() {
        return Database.query("booking_state.index").getResultList();
    }

    public static BookingState get(UUID id) {
        return Database.find(BookingState.class, id);
    }

    public static void create(BookingState entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(BookingState entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("booking_state.count")
                .getSingleResult())
                .intValue();
    }

    public static BookingState getByName(String name) {
        return (BookingState) Database.query("booking_state.get_by_name")
                .setParameter("name", name)
                .getResultStream().findFirst().orElse(null);
    }
}


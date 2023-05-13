package pt.ipvc.bll;

import jakarta.persistence.Query;
import pt.ipvc.dal.Payment;
import pt.ipvc.dal.Trip;
import pt.ipvc.database.Database;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


public class PaymentBLL {

    public static List<Payment> index() {
        return Database.query("payment.index").getResultList();
    }

    public static Payment get(UUID id) {
        return Database.find(Payment.class, id);
    }

    public static void create(Payment entity) {
        Database.beginTransaction();
        Database.insert(entity);
        Database.commitTransaction();
    }

    public static void update(Payment entity) {
        Database.beginTransaction();
        Database.update(entity);
        Database.commitTransaction();
    }

    public static void remove(UUID id) {
        Payment entity = get(id);

        Database.beginTransaction();
        Database.delete(entity);
        Database.commitTransaction();
    }

    public static int count() {
        return ((Long) Database.query("payment.count").getSingleResult()).intValue();
    }

    public static double todayProfit() {
        return (double) Database.customQuery("SELECT COALESCE(SUM(amount), 0) FROM Payment WHERE DATE(createdAt) = current_date").getSingleResult();
    }

    public static double totalProfit() {
        return (double) Database.query("payment.total_profit").getSingleResult();
    }

    public static double weekProfit() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeek = currentDate.minus(7, ChronoUnit.DAYS);

        Query query = Database.query("payment.profit_between");
        query.setParameter("startDate", Date.valueOf(lastWeek));
        query.setParameter("endDate", Date.valueOf(currentDate));
        return (double) query.getSingleResult();
    }

    public static double monthProfit() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastMonth = currentDate.minus(1, ChronoUnit.MONTHS);

        Query query = Database.query("payment.profit_between");
        query.setParameter("startDate", Date.valueOf(lastMonth));
        query.setParameter("endDate", Date.valueOf(currentDate));
        return (double) query.getSingleResult();
    }

    public static Payment getByTrip(Trip trip) {
        return (Payment) Database.query("payment.get_by_trip")
                .setParameter("id", trip.getId())
                .getResultStream().findFirst().orElse(null);
    }

    public static List<Payment> getLast10Payments() {
        return (List<Payment>) Database.query("payment.index").setMaxResults(10).getResultList();
    }
}

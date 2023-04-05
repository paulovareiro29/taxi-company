package pt.ipvc.dal;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "trips")
@NamedQueries({
        @NamedQuery(name = "trip.index", query = "SELECT trip FROM Trip trip"),
        @NamedQuery(name = "trip.count", query = "SELECT count(trip) FROM Trip trip"),
})
public class Trip {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "pickup_date", nullable = false)
    private Timestamp pickupDate;

    @Column(name = "dropoff_date", nullable = false)
    private Timestamp dropoffDate;

    @Column(name = "price", nullable = false)
    private Float price;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User employee;

    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Booking booking;

    public Trip() {}

    public Trip(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Trip[id='%s']", id);
    }

    public UUID getId() {
        return id;
    }

    public Timestamp getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Timestamp pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Timestamp getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(Timestamp dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

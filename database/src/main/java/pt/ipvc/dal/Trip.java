package pt.ipvc.dal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "trips")
@NamedQueries({
        @NamedQuery(name = "trip.index", query = "SELECT trip FROM Trip trip WHERE deletedAt = null ORDER BY trip.createdAt DESC"),
        @NamedQuery(name = "trip.count", query = "SELECT count(trip) FROM Trip trip WHERE deletedAt = null"),
        @NamedQuery(name = "trip.get_by_booking", query = "SELECT trip FROM Trip trip, Booking booking WHERE trip.booking.id = booking.id AND booking.id = :id AND trip.deletedAt = null"),
})
public class Trip {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "pickup_date", nullable = false)
    private Date pickupDate;

    @Column(name = "dropoff_date", nullable = false)
    private Date dropoffDate;

    @Column(name = "price", nullable = false)
    private Float price;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User employee;

    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Booking booking;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

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

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Timestamp pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(Date dropoffDate) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}

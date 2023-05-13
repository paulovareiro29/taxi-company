package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "booking.index", query = "SELECT booking FROM Booking booking WHERE deletedAt = null ORDER BY booking.createdAt DESC"),
        @NamedQuery(name = "booking.count", query = "SELECT count(booking) FROM Booking booking WHERE deletedAt = null"),
        @NamedQuery(name = "booking.count_state", query = "SELECT count(booking) FROM Booking booking JOIN booking.state state WHERE state.name LIKE :state AND booking.deletedAt = null")
})
public class Booking {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "pickup_date", nullable = false)
    private Date pickupDate;

    @Column(name = "extra")
    private String extra;

    @Column(name = "occupancy", nullable = false)
    private int occupancy;

    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne()
    private User client;

    @JoinColumn(name = "booked_by", referencedColumnName = "id", nullable = false)
    @ManyToOne()
    private User bookedBy;

    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BookingState state;

    @JoinColumn(name = "taxi_id", referencedColumnName = "id")
    @ManyToOne()
    private Taxi taxi;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public Booking() {}

    public Booking(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Booking[id=%s, origin='%s', destination='%s', pickupDate='%s', occupancy='%d', client='%s', bookedBy='%s' state='%s', extra='%s']",
                id, origin, destination, pickupDate, occupancy, client, bookedBy, state, extra);
    }

    public UUID getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(User bookedBy) {
        this.bookedBy = bookedBy;
    }

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
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

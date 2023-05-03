package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "booking.index", query = "SELECT booking FROM Booking booking"),
        @NamedQuery(name = "booking.count", query = "SELECT count(booking) FROM Booking booking")
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
    private Timestamp pickupDate;

    @Column(name = "extra")
    private String extra;

    @Column(name = "occupancy", nullable = false)
    private int occupancy;

    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = true)
    @ManyToOne()
    private User user;

    @JoinColumn(name = "state_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BookingState state;

    @JoinColumn(name = "taxi_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Taxi taxi;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Booking() {}

    public Booking(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Booking[id=%s, origin='%s', destination='%s', pickupDate='%s', occupancy='%d', client='%s', state='%s', extra='%s']",
                id, origin, destination, pickupDate, occupancy, user, state, extra);
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

    public Timestamp getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Timestamp pickupDate) {
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
        return user;
    }

    public void setClient(User user) {
        this.user = user;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}

package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "feedbacks")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "feedback.index", query = "SELECT feedback FROM Feedback feedback"),
        @NamedQuery(name = "feedback.count", query = "SELECT count(feedback) FROM Feedback feedback"),
        @NamedQuery(name = "feedback.get_by_trip", query = "SELECT feedback FROM Feedback feedback, Trip trip WHERE feedback.trip.id = trip.id AND trip.id = :id"),
})
public class Feedback implements Serializable {

    @Id
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Trip trip;

    @Id
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User client;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "review")
    private String review;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    public Feedback() {}

    public Feedback(Trip trip, User client) {
        this.trip = trip;
        this.client = client;
    }

    @Override
    public String toString() {
        return String.format("Feedback[trip='%s', client='%s']", trip, client);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

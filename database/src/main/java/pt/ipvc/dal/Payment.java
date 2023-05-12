package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "payment.index", query = "SELECT payment FROM Payment payment"),
        @NamedQuery(name = "payment.count", query = "SELECT count(payment) FROM Payment payment"),
        @NamedQuery(name = "payment.get_by_trip", query = "SELECT payment FROM Payment payment, Trip trip WHERE payment.trip.id = trip.id AND trip.id = :id")
})
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "vat")
    private int vat;

    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Trip trip;

    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaymentMethod paymentMethod;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    public Payment() {}

    public Payment(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Payment[id=%s]", id);
    }

    public UUID getId() {
        return id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public PaymentMethod getPaymentType() {
        return paymentMethod;
    }

    public void setPaymentType(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

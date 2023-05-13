package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "payment.index", query = "SELECT payment FROM Payment payment ORDER BY payment.createdAt DESC"),
        @NamedQuery(name = "payment.count", query = "SELECT count(payment) FROM Payment payment"),
        @NamedQuery(name = "payment.get_by_trip", query = "SELECT payment FROM Payment payment, Trip trip WHERE payment.trip.id = trip.id AND trip.id = :id"),
        @NamedQuery(name = "payment.total_profit", query = "SELECT coalesce(sum(payment.amount), 0) FROM Payment payment"),
        @NamedQuery(name = "payment.profit_between", query = "SELECT COALESCE(SUM(amount), 0) FROM Payment WHERE createdAt >= :startDate AND createdAt <= :endDate")
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
    private Date createdAt;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

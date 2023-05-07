package pt.ipvc.dal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment_types")
@NamedQueries({
        @NamedQuery(name = "payment_type.index", query = "SELECT payment_type FROM PaymentType payment_type WHERE deletedAt = null"),
        @NamedQuery(name = "payment_type.count", query = "SELECT count(payment_type) FROM PaymentType payment_type WHERE deletedAt = null"),
        @NamedQuery(name = "payment_type.get_by_name", query = "SELECT payment_type FROM PaymentType payment_type WHERE payment_type.name LIKE :name AND deletedAt = null"),
})
public class PaymentType {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public PaymentType() {}

    public PaymentType(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("PaymentType[id='%s', name='%s', description='%s']", id, name, description);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

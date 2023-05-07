package pt.ipvc.dal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment_methods")
@NamedQueries({
        @NamedQuery(name = "payment_method.index", query = "SELECT payment_method FROM PaymentMethod payment_method WHERE deletedAt = null"),
        @NamedQuery(name = "payment_method.count", query = "SELECT count(payment_method) FROM PaymentMethod payment_method WHERE deletedAt = null"),
        @NamedQuery(name = "payment_method.get_by_name", query = "SELECT payment_method FROM PaymentMethod payment_method WHERE payment_method.name LIKE :name AND deletedAt = null"),
})
public class PaymentMethod {

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

    public PaymentMethod() {}

    public PaymentMethod(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("PaymentMethod[id='%s', name='%s', description='%s']", id, name, description);
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

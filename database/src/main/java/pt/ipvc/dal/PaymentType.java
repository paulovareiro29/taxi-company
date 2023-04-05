package pt.ipvc.dal;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "payment_types")
@NamedQueries({
        @NamedQuery(name = "payment_type.index", query = "SELECT payment_type FROM PaymentType payment_type"),
        @NamedQuery(name = "payment_type.count", query = "SELECT count(payment_type) FROM PaymentType payment_type"),
        @NamedQuery(name = "payment_type.get_by_name", query = "SELECT payment_type FROM PaymentType payment_type WHERE payment_type.name LIKE :name"),
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
}

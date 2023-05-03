package pt.ipvc.dal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "models")
@NamedQueries({
        @NamedQuery(name = "model.index", query = "SELECT model FROM Model model WHERE deletedAt = null"),
        @NamedQuery(name = "model.count", query = "SELECT count(model) FROM Model model WHERE deletedAt = null"),
        @NamedQuery(name = "model.get_by_name", query = "SELECT model FROM Model model WHERE model.name LIKE :name AND deletedAt = null"),
        @NamedQuery(name = "model.get_by_brand", query = "SELECT model FROM Model model JOIN FETCH model.brand brand WHERE cast(brand.id AS string) LIKE :brand_id AND model.deletedAt = null"),
})
public class Model {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Brand brand;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Model() {}

    public Model(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Model[id=%s, name='%s']", id, name);
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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

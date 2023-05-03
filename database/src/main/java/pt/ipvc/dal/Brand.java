package pt.ipvc.dal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "brands")
@NamedQueries({
        @NamedQuery(name = "brand.index", query = "SELECT brand FROM Brand brand"),
        @NamedQuery(name = "brand.count", query = "SELECT count(brand) FROM Brand brand"),
        @NamedQuery(name = "brand.get_by_name", query = "SELECT brand FROM Brand brand WHERE brand.name LIKE :name"),
})
public class Brand {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Brand() {}

    public Brand(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Brand[id=%s, name='%s']", id, name);
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

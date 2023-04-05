package pt.ipvc.dal;

import jakarta.persistence.*;

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
}

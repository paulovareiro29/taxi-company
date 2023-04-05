package pt.ipvc.dal;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "models")
@NamedQueries({
        @NamedQuery(name = "model.index", query = "SELECT model FROM Model model"),
        @NamedQuery(name = "model.count", query = "SELECT count(model) FROM Model model"),
        @NamedQuery(name = "model.get_by_name", query = "SELECT model FROM Model model WHERE model.name LIKE :name"),
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
}

package pt.ipvc.dal;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "taxis")
@NamedQueries({
        @NamedQuery(name = "taxi.index", query = "SELECT taxi FROM Taxi taxi"),
        @NamedQuery(name = "taxi.count", query = "SELECT count(taxi) FROM Taxi taxi"),
})
public class Taxi {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "max_occupancy", nullable = false)
    private int maxOccupancy;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "color")
    private String color;

    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Model model;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Taxi() {}

    public Taxi(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Taxi[id='%s']", id);
    }

    public UUID getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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

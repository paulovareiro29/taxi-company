package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.UUID;

@Entity
@XmlRootElement
@Table(name = "booking_states")
@NamedQueries({
        @NamedQuery(name = "booking_state.index", query = "SELECT state FROM BookingState state"),
        @NamedQuery(name = "booking_state.count", query = "SELECT count(state) FROM BookingState state"),
        @NamedQuery(name = "booking_state.get_by_name", query = "SELECT state FROM BookingState state WHERE state.name LIKE :name")
})
public class BookingState {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public BookingState() {}

    public BookingState(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("BookingState[id=%s, name='%s', description='%s']", id, name, description);
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

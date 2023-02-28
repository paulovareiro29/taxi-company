package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "booking_state")
@NamedQueries({
        @NamedQuery(name = "booking_state.index", query = "SELECT state FROM BookingState state"),
        @NamedQuery(name = "booking_state.count", query = "SELECT count(state) FROM BookingState state"),
        @NamedQuery(name = "booking_state.get_by_name", query = "SELECT state FROM BookingState state WHERE state.name LIKE :name")
})
public class BookingState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public BookingState() {}

    public BookingState(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("BookingState[id=%d, name='%s', description='%s']", id, name, description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.UUID;

@Entity
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "role.index", query = "SELECT role FROM Role role"),
        @NamedQuery(name = "role.count", query = "SELECT count(role) FROM Role role"),
        @NamedQuery(name = "role.get_by_name", query = "SELECT role FROM Role role WHERE role.name LIKE :name"),
})
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public Role() {}

    public Role(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Role[id=%s, name='%s', description='%s']", id, name, description);
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

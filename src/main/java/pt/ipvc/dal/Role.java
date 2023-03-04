package pt.ipvc.dal;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Role() {}

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Role[id=%d, name='%s', description='%s']", id, name, description);
    }

    public Long getId() {
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

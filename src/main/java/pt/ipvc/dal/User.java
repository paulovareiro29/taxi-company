package pt.ipvc.dal;


import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "user.index", query = "SELECT user FROM User user"),
        @NamedQuery(name = "user.count", query = "SELECT count(user) FROM User user"),
        @NamedQuery(name = "user.client-index", query = "SELECT user FROM User user, Role role WHERE user.role.id = role.id AND role.name LIKE 'client'")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Role role;

    public User() {}

    public User(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', phone='%s', email='%s', role='%s']", id, name, phone, email, role.getName());
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

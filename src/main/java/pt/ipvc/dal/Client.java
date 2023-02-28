package pt.ipvc.dal;


import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "client.index", query = "SELECT client FROM Client client"),
        @NamedQuery(name = "client.count", query = "SELECT count(client) FROM Client client")
})
public class Client {

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

    public Client() {}

    public Client(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Client[id=%d, name='%s', phone='%s', email='%s']", id, name, phone, email);
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
}

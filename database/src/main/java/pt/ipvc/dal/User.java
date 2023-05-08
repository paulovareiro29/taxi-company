package pt.ipvc.dal;


import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "user.index", query = "SELECT user FROM User user WHERE user.deletedAt = null"),
        @NamedQuery(name = "user.count", query = "SELECT count(user) FROM User user WHERE user.deletedAt = null"),
        @NamedQuery(name = "user.get_by_email", query = "SELECT user FROM User user WHERE user.email LIKE :email AND user.deletedAt = null"),
        @NamedQuery(name = "user.client_index", query = "SELECT user FROM User user, Role role WHERE user.role.id = role.id AND role.name LIKE 'client' AND user.deletedAt = null"),
        @NamedQuery(name = "user.driver_index", query = "SELECT user FROM User user, Role role WHERE user.role.id = role.id AND role.name LIKE 'driver' AND user.deletedAt = null")
})
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "vat")
    private int VAT;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Role role;

    public User() {}

    public User(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s, name='%s', email='%s',phone='%s',  role='%s', createdAt='%s']", id, name, email, phone, role.getName(), createdAt.toLocalDateTime());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
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

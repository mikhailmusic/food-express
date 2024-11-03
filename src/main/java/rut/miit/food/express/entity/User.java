package rut.miit.food.express.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String firstName;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String login;
    private String password;
    private Set<Order> orders;
    private Set<Review> reviews;

    public User(String firstName, String phoneNumber, String address, LocalDate birthDate, String login, String password) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
        this.login = login;
        this.password = password;
    }

    protected User() {
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "phone_number", nullable = false, unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    @Column(name = "birth_date", nullable = false)
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Column(name = "login", nullable = false, unique = true)
    public String getLogin() {
        return login;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @OneToMany(mappedBy = "user")
    public Set<Order> getOrders() {
        return orders;
    }

    @OneToMany(mappedBy = "user")
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}

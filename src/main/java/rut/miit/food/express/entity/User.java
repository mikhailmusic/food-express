package rut.miit.food.express.entity;

import jakarta.persistence.*;
import rut.miit.food.express.exception.InvalidValueException;

import java.time.LocalDate;
import java.time.Period;
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

    public User(String firstName, String login, String address, LocalDate birthDate, String password, String confirmPassword, Set<String> existingLogins) {
        setLogin(login, existingLogins);
        setFirstName(firstName);
        setAddress(address);
        setBirthDate(birthDate);
        setPassword(password, confirmPassword);
    }

    protected User() {
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "phone_number", unique = true)
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
        if (firstName == null || firstName.trim().length() < 2) {
            throw new InvalidValueException("First Name must be at least 2 characters and not null");
        }
        this.firstName = firstName;
    }

    protected void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.isBlank() && !phoneNumber.matches("\\+?[0-9-]+")) {
            throw new InvalidValueException("Phone number must only contain digits and optional '+'");
        }
        this.phoneNumber = (phoneNumber != null && phoneNumber.isBlank()) ? null : phoneNumber;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().length() < 10) {
            throw new InvalidValueException("Address must be at least 10 characters long and not null");
        }
        this.address = address;
    }

    protected void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new InvalidValueException("Birth date must not be null");
        }
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);

        if (period.getYears() < 14) {
            throw new InvalidValueException("Person must be at least 14 years old");
        }
        this.birthDate = birthDate;
    }

    protected void setLogin(String login) {
        if (login == null || login.trim().length() < 5) {
            throw new InvalidValueException("Login must not be null and must be at least 5 characters long");
        }
        this.login = login;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    protected void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!password.equals(oldPassword)) {
            throw new InvalidValueException("Old password was entered incorrectly");
        }
        setPassword(newPassword, confirmPassword);
    }

    private void setPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new InvalidValueException("Password and its confirmation do not match");
        }

        if (!isPasswordValid(password)) {
            throw new InvalidValueException("Password is not secure");
        }

        this.password = password;
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return true;
    }

    protected void setLogin(String login, Set<String> existingLogins) {
        if (existingLogins.contains(login)) {
            throw new InvalidValueException("Login is incorrect");
        }
        setLogin(login);
    }

    public void setPhoneNumber(String phoneNumber, Set<String> existingPhoneNumbers) {
        if (this.phoneNumber != null) {
            existingPhoneNumbers.remove(this.phoneNumber);
        }

        if (phoneNumber != null && existingPhoneNumbers.contains(phoneNumber)) {
            throw new InvalidValueException("Phone number already exists: " + phoneNumber);
        }
        setPhoneNumber(phoneNumber);
    }
}

package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import rut.miit.food.express.exception.InvalidValueException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{
    private String name;
    private String address;
    private String description;
    private String phoneNumber;
    private LocalTime openTime;
    private LocalTime closeTime;
    private BigDecimal minOrderAmount;
    private Set<Order> orders;
    private Set<Dish> dishes;

    public Restaurant(String name, String address, String description, String phoneNumber, LocalTime openTime, LocalTime closeTime, BigDecimal minOrderAmount) {
        setName(name);
        setAddress(address);
        setDescription(description);
        setPhoneNumber(phoneNumber);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setMinOrderAmount(minOrderAmount);
    }

    protected Restaurant() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "address", nullable = false, unique = true)
    public String getAddress() {
        return address;
    }

    @Column(name = "description", length = 700)
    public String getDescription() {
        return description;
    }

    @Column(name = "phone_number", nullable = false, unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "open_time", nullable = false)
    public LocalTime getOpenTime() {
        return openTime;
    }

    @Column(name = "close_time", nullable = false)
    public LocalTime getCloseTime() {
        return closeTime;
    }

    @Column(name = "min_order_amount", nullable = false)
    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    @OneToMany(mappedBy = "restaurant")
    public Set<Order> getOrders() {
        return orders;
    }

    @OneToMany(mappedBy = "restaurant")
    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new InvalidValueException("Name must be at least 2 characters and not null");
        }
        this.name = name;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().length() < 10) {
            throw new InvalidValueException("Address must be at least 10 characters long and not null");
        }
        this.address = address;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 700) throw new InvalidValueException("Description must be at most 700 characters");
        this.description = description;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.matches("\\+?[0-9-]+")) {
            throw new InvalidValueException("Phone number must only contain digits and optional '+'");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setOpenTime(LocalTime openTime) {
        if (openTime == null) {
            throw new InvalidValueException("Open time must not be null");
        }
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        if (closeTime == null) {
            throw new InvalidValueException("Close time must not be null");
        }
        this.closeTime = closeTime;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        if (minOrderAmount == null || minOrderAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidValueException("Min order amount must not be null and must be 0 or greater");
        }
        this.minOrderAmount = minOrderAmount;
    }

    protected void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    protected void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }


    public boolean checkIsOpenNow() {
        int openMinutes = openTime.toSecondOfDay() / 60;
        int closeMinutes = closeTime.toSecondOfDay() / 60;
        int currentMinutes = LocalTime.now().toSecondOfDay() / 60;

        // Если время закрытия позже времени открытия (через полуночь)
        if (closeMinutes < openMinutes) {
            return currentMinutes >= openMinutes || currentMinutes < closeMinutes;
        } else {
            return currentMinutes >= openMinutes && currentMinutes < closeMinutes;
        }
    }
}

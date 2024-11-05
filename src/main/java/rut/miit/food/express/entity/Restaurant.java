package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
        this.name = name;
        this.address = address;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
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

    @Column(name = "min_order_amount")
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
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }


    public boolean isOpenNow() {
        LocalTime currentTime = LocalTime.now();
        return !currentTime.isBefore(openTime) && currentTime.isBefore(closeTime);
    }
}

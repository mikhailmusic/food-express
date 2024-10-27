package rut.miit.food.express.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private LocalDateTime creationTime;
    private LocalDateTime deliveryTime;
    private User user;
    private Restaurant restaurant;
    private Status status;
    private Set<OrderItem> orderItems;
    private Review review;

    public Order(User user, Restaurant restaurant, Status status) {
        this.creationTime = LocalDateTime.now();
        this.user = user;
        this.restaurant = restaurant;
        this.status = status;
        this.orderItems = new HashSet<>();
    }

    protected Order() {
    }

    @Column(name = "creation_time", nullable = false)
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Column(name = "delivery_time")
    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }
    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    public Status getStatus() {
        return status;
    }

    @OneToMany(mappedBy = "order")
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    @OneToOne(mappedBy = "order")
    public Review getReview() {
        return review;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}

package rut.miit.food.express.entity;

import jakarta.persistence.*;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.exception.InvalidValueException;
import rut.miit.food.express.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{
    private String text;
    private Integer rating;
    private LocalDateTime date;
    private Order order;
    private User user;

    public Review(String text, Integer rating, Order order, User user) {
        validateState(order);
        setText(text);
        setRating(rating);
        setDate(LocalDateTime.now());
        setOrder(order);
        setUser(user);
    }

    protected Review() {
    }

    @Column(name = "text", length = 500)
    public String getText() {
        return text;
    }

    @Column(name = "rating", nullable = false)
    public Integer getRating() {
        return rating;
    }

    @Column(name = "date", nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    public Order getOrder() {
        return order;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    protected void setText(String text) {
        if (text != null && text.trim().length() > 500) {
            throw new InvalidValueException("Text must be at most 500 characters long");
        }

        this.text = (text != null && text.isBlank()) ? null : text;
    }

    protected void setRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new InvalidValueException("Rating must be between 1 and 5 and not null");
        }
        this.rating = rating;
    }

    protected void setDate(LocalDateTime date) {
        if (date == null) {
            throw new InvalidValueException("Date must not be null");
        }
        this.date = date;
    }

    protected void setOrder(Order order) {
        if (order == null) {
            throw new InvalidValueException("Order must not be null");
        }

        this.order = order;
    }

    protected void setUser(User user) {
        if (user == null) {
            throw new InvalidValueException("User must not be null");
        }
        this.user = user;
    }

    private void validateState(Order order) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime deliveryTime = order.getDeliveryTime();
        if (!order.getStatus().equals(OrderStatus.DELIVERED) || ChronoUnit.HOURS.between(deliveryTime, currentTime) > 2) {
            throw new ValidationException("Review can only be left within 2 hours after delivery");
        }
    }
}

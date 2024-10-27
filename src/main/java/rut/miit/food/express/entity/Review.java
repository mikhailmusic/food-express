package rut.miit.food.express.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{
    private String text;
    private Integer rating;
    private LocalDateTime date;
    private Order order;
    private User user;

    public Review(String text, Integer rating, LocalDateTime date, Order order, User user) {
        this.text = text;
        this.rating = rating;
        this.date = date;
        this.order = order;
        this.user = user;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

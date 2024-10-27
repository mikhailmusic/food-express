package rut.miit.food.express.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "dish")
public class Dish extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer weight;
    private Integer calories;
    private String imageURL;
    private Restaurant restaurant;
    private Category category;
    private Set<OrderItem> orderItems;

    public Dish(String name, String description, BigDecimal price, Integer weight, Integer calories, String imageURL, Restaurant restaurant, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.calories = calories;
        this.imageURL = imageURL;
        this.restaurant = restaurant;
        this.category = category;
    }

    protected Dish() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    @Column(name = "weight", nullable = false)
    public Integer getWeight() {
        return weight;
    }

    @Column(name = "calories", nullable = false)
    public Integer getCalories() {
        return calories;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageURL() {
        return imageURL;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return category;
    }

    @OneToMany(mappedBy = "dish")
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

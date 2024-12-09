package rut.miit.food.express.entity;

import jakarta.persistence.*;
import rut.miit.food.express.exception.InvalidValueException;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer weight;
    private Integer calories;
    private String imageURL;
    private Boolean isVisible;
    private Restaurant restaurant;
    private DishCategory category;
    private Set<OrderItem> orderItems;

    public Dish(String name, String description, BigDecimal price, Integer weight, Integer calories, String imageURL, Restaurant restaurant, DishCategory category) {
        setName(name);
        setDescription(description);
        setPrice(price);
        setWeight(weight);
        setCalories(calories);
        setImageURL(imageURL);
        setRestaurant(restaurant);
        setCategory(category);
        setVisible(true);
    }

    protected Dish() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", length = 500)
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

    @Column(name = "is_visible", nullable = false)
    public Boolean getVisible() {
        return isVisible;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    public DishCategory getCategory() {
        return category;
    }

    @OneToMany(mappedBy = "dish")
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new InvalidValueException("Name must be at least 2 characters and not null");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description != null && description.length() > 500) throw new InvalidValueException("Description must be at most 500 characters");
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 1) throw new InvalidValueException("Price must not be null and must be at least 1");
        this.price = price;
    }

    public void setWeight(Integer weight) {
        if (weight == null || weight < 1) throw new InvalidValueException("Weight must not be null and must be at least 1");
        this.weight = weight;
    }

    public void setCalories(Integer calories) {
        if (calories == null || calories < 1) {
            throw new InvalidValueException("Calories must not be null and must be at least 1");
        }
        this.calories = calories;
    }

    public void setImageURL(String imageURL) {
        if (imageURL == null || imageURL.trim().isEmpty()) {
            throw new InvalidValueException("Image URL must not be null, empty, or contain only whitespace");
        }
        this.imageURL = imageURL;
    }

    public void setVisible(Boolean visible) {
        if (visible == null) {
            throw new IllegalArgumentException("Visibility cannot be null");
        }
        isVisible = visible;
    }

    protected void setRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new InvalidValueException("Restaurant must not be null");
        }
        this.restaurant = restaurant;
    }

    public void setCategory(DishCategory category) {
        if (category == null) {
            throw new InvalidValueException("Category must not be null");
        }
        this.category = category;
    }

    protected void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

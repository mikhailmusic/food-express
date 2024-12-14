package rut.miit.food.express.entity;

import jakarta.persistence.*;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.exception.InvalidValueException;
import rut.miit.food.express.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private LocalDateTime creationTime;
    private LocalDateTime deliveryTime;
    private User user;
    private Restaurant restaurant;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Set<OrderItem> orderItems;
    private Review review;

    public Order(User user, Restaurant restaurant) {
        setUser(user);
        setRestaurant(restaurant);
        setStatus(OrderStatus.DRAFT);
        setOrderItems(new HashSet<>());
    }

    protected Order() {
    }

    @Column(name = "creation_time")
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Column(name = "delivery_time")
    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public OrderStatus getStatus() {
        return status;
    }

    @Column(name = "total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @OneToMany(mappedBy = "order")
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    @OneToOne(mappedBy = "order")
    public Review getReview() {
        return review;
    }

    protected void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    protected void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    protected void setUser(User user) {
        if (user == null) {
            throw new InvalidValueException("User must not be null");
        }
        this.user = user;
    }

    protected void setRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new InvalidValueException("Restaurant must not be null");
        }
        this.restaurant = restaurant;
    }

    protected void setStatus(OrderStatus status) {
        if (status == null) {
            throw new InvalidValueException("Status must not be null");
        }
        this.status = status;
    }

    protected void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    protected void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    protected void setReview(Review review) {
        this.review = review;
    }

    public OrderItem addOrder(Dish dish, Integer count) {
        if (status != OrderStatus.DRAFT)
            throw new ValidationException("Cannot be added to order because status is not DRAFT");

        if (!restaurant.ownsDish(dish)) {
            throw new ValidationException("Dish does not belong to this restaurant");
        }

        for (OrderItem existingItem : orderItems) {
            if (existingItem.getDish().equals(dish)) {
                existingItem.setCount(count);
                return existingItem;
            }
        }
        OrderItem newOrderItem = new OrderItem(dish, count, this);
        orderItems.add(newOrderItem);
        return newOrderItem;
    }

    public void cancelOrder() {
        if (status != OrderStatus.CREATED && status != OrderStatus.DRAFT) {
            throw new ValidationException("Order cannot be cancelled because it is not in CREATED status");
        }
        status = OrderStatus.CANCELLED;
    }

    public void createOrder() {
        if (!restaurant.checkIsOpenNow()) throw new ValidationException("Restaurant is now closed");

        if (status != OrderStatus.DRAFT || orderItems == null || orderItems.isEmpty()) {
            throw new ValidationException("The status is not DRAFT or there are no dishes in the order");
        }

        for (OrderItem orderItem : orderItems) {
            if (!orderItem.getDish().getVisible()) {
                throw new ValidationException("One or more dishes are unavailable");
            }
        }

        BigDecimal total = calculateTotalAmount();
        if (total.compareTo(restaurant.getMinOrderAmount()) < 0) {
            throw new ValidationException("Order amount is less than the restaurant's minimum order amount: " + restaurant.getMinOrderAmount());
        }
        creationTime = LocalDateTime.now();
        status = OrderStatus.CREATED;
        totalAmount = total;

    }

    public BigDecimal calculateTotalAmount() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getDish().getPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));
        }
        return total;
    }

    public boolean notEmpty() {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getCount() != 0) {
                return true;
            }
        }
        return false;
    }

    public void nextStatus() {
        switch (status) {
            case CREATED -> status = OrderStatus.CONFIRMED;
            case CONFIRMED -> status = OrderStatus.COOKING_PROCESS;
            case COOKING_PROCESS -> status = OrderStatus.READY_FOR_DELIVERY;
            case READY_FOR_DELIVERY -> status = OrderStatus.OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> {
                status = OrderStatus.DELIVERED;
                deliveryTime = LocalDateTime.now();
            }
            default -> throw new ValidationException("Unsupported status transition from " + status);
        }
    }
}

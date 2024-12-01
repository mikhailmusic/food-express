package rut.miit.food.express.entity;

import jakarta.persistence.*;
import rut.miit.food.express.exception.InvalidValueException;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    private Integer count;
    private Order order;
    private Dish dish;

    protected OrderItem( Dish dish, Integer count, Order order) {
        setDish(dish);
        setCount(count);
        setOrder(order);
    }

    protected OrderItem() {
    }

    @Column(name = "count", nullable = false)
    public Integer getCount() {
        return count;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    public Order getOrder() {
        return order;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "dish_id", nullable = false)
    public Dish getDish() {
        return dish;
    }

    protected void setCount(Integer count) {
        if (count == null || count < 0) {
            throw new InvalidValueException("Count must not be null and must be 0 or greater");
        }
        this.count = count;
    }

    protected void setOrder(Order order) {
        if (order == null) {
            throw new InvalidValueException("Order must not be null");
        }
        this.order = order;
    }

    protected void setDish(Dish dish) {
        if (dish == null) {
            throw new InvalidValueException("Dish must not be null");
        }
        this.dish = dish;
    }
}

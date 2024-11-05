package rut.miit.food.express.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    private Integer count;
    private Order order;
    private Dish dish;

    public OrderItem(Integer count, Order order, Dish dish) {
        this.count = count;
        this.order = order;
        this.dish = dish;
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

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}

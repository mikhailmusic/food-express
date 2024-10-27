package rut.miit.food.express.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    private BigDecimal orderPrice;
    private Integer count;
    private Order order;
    private Dish dish;

    public OrderItem(BigDecimal orderPrice, Integer count, Dish dish) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.dish = dish;
    }

    protected OrderItem() {
    }

    @Column(name = "order_price", nullable = false)
    public BigDecimal getOrderPrice() {
        return orderPrice;
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

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
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

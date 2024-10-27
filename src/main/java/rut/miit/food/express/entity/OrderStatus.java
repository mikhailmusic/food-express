package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "order_statuses")
public class OrderStatus extends BaseEntity {
    private String status;
    private Set<Order> orders;

    public OrderStatus(String status) {
        this.status = status;
    }

    protected OrderStatus() {
    }

    @Column(name = "status", nullable = false, unique = true)
    public String getStatus() {
        return status;
    }

    @OneToMany(mappedBy = "status")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}

package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "status")
public class Status extends BaseEntity {
    private String status;
    private Set<Order> orders;

    public Status(String status) {
        this.status = status;
    }

    protected Status() {
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

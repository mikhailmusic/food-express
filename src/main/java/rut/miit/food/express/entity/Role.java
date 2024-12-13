package rut.miit.food.express.entity;

import jakarta.persistence.*;
import rut.miit.food.express.entity.enums.UserRole;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private UserRole name;

    public Role(UserRole name) {
        this.name = name;
    }

    protected Role() {
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true)
    public UserRole getName() {
        return name;
    }

    protected void setName(UserRole name) {
        this.name = name;
    }
}

package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    private String name;
    private Set<Dish> dishes;

    public Category(String name) {
        this.name = name;
    }

    protected Category() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "category")
    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }
}


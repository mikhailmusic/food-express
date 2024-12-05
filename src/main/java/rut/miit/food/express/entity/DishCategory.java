package rut.miit.food.express.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import rut.miit.food.express.exception.InvalidValueException;

import java.util.Set;

@Entity
@Table(name = "dish_categories")
public class DishCategory extends BaseEntity{
    private String name;
    private Set<Dish> dishes;

    public DishCategory(String name, Set<String> existingCategoryNames) {
        setName(name, existingCategoryNames);
    }

    protected DishCategory() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "category")
    public Set<Dish> getDishes() {
        return dishes;
    }

    protected void setName(String name) {
        if (name == null || name.trim().length() < 2) {
            throw new InvalidValueException("Name must be at least 2 characters and not null");
        }
        this.name = name;
    }

    protected void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    protected void setName(String name, Set<String> existingCategoryNames) {
        if (this.name != null) {
            existingCategoryNames.remove(this.name);
        }

        if (name != null && existingCategoryNames.contains(name)) {
            throw new InvalidValueException("Category with this name already exists: " + name);
        }
        setName(name);
    }
}


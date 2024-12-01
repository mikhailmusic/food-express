package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Dish;
import rut.miit.food.express.repository.DishRepository;

import java.util.List;

@Repository
public class DishRepositoryImpl extends BaseRepository<Dish, Integer> implements DishRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public DishRepositoryImpl() {
        super(Dish.class);
    }

    @Override
    public List<Dish> findByNameContaining(String name, Integer categoryId) {
        return entityManager.createQuery("SELECT d FROM Dish d WHERE LOWER(d.name) LIKE LOWER(:name) AND (:categoryId IS NULL OR d.category.id = :categoryId)", Dish.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public List<Dish> findByRestaurantId(Integer restaurantId) {
        return entityManager.createQuery("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId", Dish.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }
}

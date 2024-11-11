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
    public List<Dish> findByNameContaining(String namePart) {
        return entityManager.createQuery("SELECT d FROM Dish d WHERE d.name LIKE :namePart", Dish.class)
                .setParameter("namePart", "%" + namePart + "%")
                .getResultList();
    }

    @Override
    public List<Dish> findByRestaurantId(Integer restaurantId) {
        return entityManager.createQuery("SELECT b FROM Dish b WHERE b.restaurant.id = :restaurantId", Dish.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }
}

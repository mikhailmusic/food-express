package rut.miit.food.express.repository.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.DishCategory;
import rut.miit.food.express.repository.DishCategoryRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DishCategoryRepositoryImpl extends BaseRepository<DishCategory, Integer> implements DishCategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public DishCategoryRepositoryImpl() {
        super(DishCategory.class);
    }

    @Override
    public List<DishCategory> findByRestaurantId(Integer restaurantId) {
        return entityManager.createQuery("SELECT DISTINCT d.category FROM Dish d WHERE d.restaurant.id = :restaurantId", DishCategory.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Override
    public Optional<DishCategory> findByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("SELECT c FROM DishCategory c WHERE c.name = :name", DishCategory.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}

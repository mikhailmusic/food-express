package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Restaurant;
import rut.miit.food.express.repository.RestaurantRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class RestaurantRepositoryImpl extends BaseRepository<Restaurant, Integer> implements RestaurantRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public RestaurantRepositoryImpl() {
        super(Restaurant.class);
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("SELECT r FROM Restaurant r WHERE r.name = :name", Restaurant.class)
                    .setParameter("name", name)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Restaurant> findByNameContaining(String name) {
        return entityManager.createQuery("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(:name)", Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<Restaurant> findAllWithOrders() {
        return entityManager.createQuery("SELECT r FROM Restaurant r LEFT JOIN FETCH r.orders o LEFT JOIN FETCH o.review", Restaurant.class)
                .getResultList();
    }

    @Override
    public Set<String> findAllNames() {
        return new HashSet<>(entityManager.createQuery("SELECT r.name FROM Restaurant r", String.class).getResultList());
    }
}

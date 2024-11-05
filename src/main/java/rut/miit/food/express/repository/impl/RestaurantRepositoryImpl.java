package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Restaurant;
import rut.miit.food.express.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

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
    public List<Restaurant> findByAddressContaining(String addressPart) {
        return entityManager.createQuery("SELECT r FROM Restaurant r WHERE r.address LIKE :addressPart", Restaurant.class)
                .setParameter("addressPart", "%" + addressPart + "%")
                .getResultList();
    }
}

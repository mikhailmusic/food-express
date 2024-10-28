package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Review;
import rut.miit.food.express.repository.ReviewRepository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl extends BaseRepository<Review, Integer> implements ReviewRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

    @Override
    public List<Review> findAllByRestaurantId(Integer restaurantId) {
        return entityManager.createQuery("SELECT r FROM Review r JOIN r.order o WHERE o.restaurant.id = :restaurantId", Review.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }
}

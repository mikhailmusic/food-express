package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Order;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public class OrderRepositoryImpl extends BaseRepository<Order, Integer> implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findByRestaurantTimeBetween(Integer restaurantId, LocalDateTime start, LocalDateTime end) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.creationTime BETWEEN :start AND :end", Order.class)
                .setParameter("restaurantId", restaurantId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public List<Order> findByUserId(Integer userId, LocalDateTime start, LocalDateTime end) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId AND o.creationTime BETWEEN :start AND :end", Order.class)
                .setParameter("userId", userId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
    // SELECT o FROM Order o WHERE o.user.id = :userId AND o.creationTime >= :dateFrom

    @Override
    public List<Order> findByUserIdStatus(Integer userId, OrderStatus status) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status =: status", Order.class)
                .setParameter("userId", userId)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<Order> findByRestaurantIdStatus(Integer restaurantId, Set<OrderStatus> statuses) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.restaurant.id = :restaurantId AND o.status IN :statuses", Order.class)
                .setParameter("restaurantId", restaurantId)
                .setParameter("statuses", statuses)
                .getResultList();
    }
}

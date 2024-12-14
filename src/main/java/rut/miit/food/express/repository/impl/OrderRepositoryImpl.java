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
    public List<Order> findByUser(String username, LocalDateTime start, LocalDateTime end) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.login = :username AND o.creationTime BETWEEN :start AND :end", Order.class)
                .setParameter("username", username)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public List<Order> findByUserAndStatus(String username, OrderStatus status) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.user.login = :username AND o.status =: status", Order.class)
                .setParameter("username", username)
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

package rut.miit.food.express.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.Order;
import rut.miit.food.express.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends BaseRepository<Order, Integer> implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findAllByTimeBetween(LocalDateTime start, LocalDateTime end, Integer restaurantId) {   //                                //
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.creationTime BETWEEN :start AND :end AND o.restaurant.id = :restaurantId", Order.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }
}

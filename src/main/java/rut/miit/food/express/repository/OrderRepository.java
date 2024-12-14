package rut.miit.food.express.repository;

import rut.miit.food.express.entity.Order;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface OrderRepository extends
        CreateRepository<Order, Integer>, ReadRepository<Order, Integer>, UpdateRepository<Order, Integer> {

    List<Order> findByRestaurantTimeBetween(Integer restaurantId, LocalDateTime start, LocalDateTime end);
    List<Order> findByUser(String username, LocalDateTime start, LocalDateTime end);
    List<Order> findByUserAndStatus(String username, OrderStatus status);
    List<Order> findByRestaurantIdStatus(Integer restaurantId, Set<OrderStatus> statuses);
}


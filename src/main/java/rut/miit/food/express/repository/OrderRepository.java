package rut.miit.food.express.repository;

import rut.miit.food.express.entity.Order;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends
        CreateRepository<Order, Integer>, ReadRepository<Order, Integer>, UpdateRepository<Order, Integer> {

    List<Order> findAllByTimeBetween(LocalDateTime start, LocalDateTime end, Integer restaurantId);

}

package rut.miit.food.express.repository;

import rut.miit.food.express.entity.OrderItem;
import rut.miit.food.express.repository.generic.CreateRepository;
import rut.miit.food.express.repository.generic.ReadRepository;
import rut.miit.food.express.repository.generic.UpdateRepository;


public interface OrderItemRepository extends
        CreateRepository<OrderItem, Integer>, ReadRepository<OrderItem, Integer>, UpdateRepository<OrderItem, Integer> {

}


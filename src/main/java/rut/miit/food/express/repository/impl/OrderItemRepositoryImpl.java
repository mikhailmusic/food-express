package rut.miit.food.express.repository.impl;

import org.springframework.stereotype.Repository;
import rut.miit.food.express.entity.OrderItem;
import rut.miit.food.express.repository.OrderItemRepository;

@Repository
public class OrderItemRepositoryImpl extends BaseRepository<OrderItem, Integer> implements OrderItemRepository {

    public OrderItemRepositoryImpl() {
        super(OrderItem.class);
    }
}

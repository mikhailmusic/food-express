package rut.miit.food.express.service;

import rut.miit.food.express.dto.order.OrderItemAddDto;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.order.OrderItemUpdateDto;

import java.util.List;

public interface OrderDomainService {
    void addOrderItemToOrder(OrderItemAddDto itemDto);
    void changeOrderItemToOrder(OrderItemUpdateDto itemDto);
    void createOrder(Integer id);
    void cancelOrder(Integer id);
    OrderDto getOrderDetails(Integer id);
    List<OrderDto> userOrdersDraft(Integer userId);
    List<OrderDto> restaurantOrders(Integer userId);

    List<OrderDto> userOrdersHistory(Integer userId);

}

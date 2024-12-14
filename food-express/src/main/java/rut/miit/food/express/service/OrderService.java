package rut.miit.food.express.service;

import rut.miit.food.express.dto.order.OrderItemAddDto;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.order.OrderItemUpdateDto;

import java.util.List;

public interface OrderService {
    void addOrderItemToOrder(OrderItemAddDto itemDto);
    void changeOrderItemToOrder(OrderItemUpdateDto itemDto);
    void createOrder(Integer id, String username);
    void cancelOrder(Integer id, String username);
    void changeStatus(Integer id);

    OrderDto getOrderDetails(Integer id);
    List<OrderDto> userOrdersDraft(String username);
    List<OrderDto> restaurantOrders(Integer restaurantId);
    List<OrderDto> userOrdersHistory(String username);

}

package rut.miit.food.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.food.express.dto.order.OrderItemAddDto;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.order.OrderItemDto;
import rut.miit.food.express.dto.order.OrderItemUpdateDto;
import rut.miit.food.express.dto.review.ReviewDto;
import rut.miit.food.express.entity.*;
import rut.miit.food.express.entity.enums.OrderStatus;
import rut.miit.food.express.exception.NotFoundException;
import rut.miit.food.express.repository.DishRepository;
import rut.miit.food.express.repository.OrderItemRepository;
import rut.miit.food.express.repository.OrderRepository;
import rut.miit.food.express.repository.UserRepository;
import rut.miit.food.express.service.OrderDomainService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


@Service
public class OrderDomainServiceImpl implements OrderDomainService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderDomainServiceImpl(OrderRepository orderRepository, UserRepository userRepository, DishRepository dishRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void addOrderItemToOrder(OrderItemAddDto itemDto) {
        User user = userRepository.findById(itemDto.userId())
                .orElseThrow(() -> new NotFoundException("User Not Found: " + itemDto.userId()));
        Dish dish = dishRepository.findById(itemDto.dishId())
                    .orElseThrow(() -> new NotFoundException("Dish Not Found: " + itemDto.dishId()));
        Restaurant restaurant = dish.getRestaurant();
        Order order = user.getOrders().stream()
                .filter(o -> o.getStatus().equals(OrderStatus.DRAFT) && o.getRestaurant().equals(restaurant))
                .findFirst().orElse(null);
        if (order == null) order = new Order(user, restaurant);
        orderRepository.save(order);
        OrderItem item = order.addOrder(dish, itemDto.count());
        orderItemRepository.save(item);
    }

    @Override
    public void changeOrderItemToOrder(OrderItemUpdateDto dto) {
        OrderItem item = orderItemRepository.findById(dto.id()).orElseThrow(() -> new NotFoundException("OrderItem Not Found: " + dto.id()));
        Order order = item.getOrder();
        order.addOrder(item.getDish(), dto.newCount());
        orderItemRepository.update(item);
    }

    @Override
    public void createOrder(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order Not Found: " + id));
        order.createOrder();
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order Not Found: " + id));
        order.cancelOrder();
        orderRepository.update(order);
    }

    @Override
    public OrderDto getOrderDetails(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order Not Found: " + id));
        return toDto(order);
    }

    @Override
    public List<OrderDto> userOrdersDraft(Integer userId) {
        List<Order> orders = orderRepository.findByUserIdStatus(userId, OrderStatus.DRAFT);
        return orders.stream().filter(Order::notEmpty).map(this::toDto).toList();
    }

    @Override
    public List<OrderDto> restaurantOrders(Integer userId) {
        List<Order> orders = orderRepository.findByRestaurantIdStatus(userId, Set.of(OrderStatus.CREATED,
                OrderStatus.CONFIRMED, OrderStatus.COOKING_PROCESS, OrderStatus.READY_FOR_DELIVERY)
        );
        return orders.stream().sorted(Comparator.comparing(Order::getCreationTime)).map(this::toDto).toList();
    }

    @Override
    public List<OrderDto> userOrdersHistory(Integer userId) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime startTime = nowTime.minusDays(90);
        List<Order> orders = orderRepository.findByUserId(userId, startTime, nowTime);
        return orders.stream().sorted(Comparator.comparing(Order::getCreationTime).reversed()).map(this::toDto).toList();
    }



    private OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        List<OrderItemDto> orderItems = order.getOrderItems().stream()
                .filter(item -> item.getCount() != 0)
                .map(item -> new OrderItemDto(item.getId(), item.getDish().getId(), item.getCount(),
                        item.getDish().getName(), item.getDish().getImageURL()))
                .sorted(Comparator.comparing(OrderItemDto::dishName)).toList();
        ReviewDto reviewDto = null;
        if (order.getReview() != null){
            reviewDto = new ReviewDto(order.getReview().getText(), order.getReview().getRating(), order.getReview().getDate());
        }
        return new OrderDto(order.getId(), order.getCreationTime(), order.getDeliveryTime(), order.getStatus(),
                order.getRestaurant().getId(), order.getRestaurant().getName(), orderItems, reviewDto);
    }

//    @Override
//    public OrderDto placeOrder(OrderDto orderDto) {
//        User user = userRepository.findById(orderDto.getUserId())
//                .orElseThrow(() -> new NotFoundException("User Not Found: " + orderDto.getUserId()));
//        Restaurant restaurant = restaurantRepository.findById(orderDto.getRestaurantId())
//                .orElseThrow(() -> new NotFoundException("Restaurant Not Found: " + orderDto.getRestaurantId()));
//
//        if (!restaurant.checkIsOpenNow()) throw new ValidationException("Restaurant is now closed");
//        if (orderDto.getOrderItems() == null || orderDto.getOrderItems().isEmpty()) {
//            throw new ValidationException("Order must contain at least one dish");
//        }
//
//        Order order = new Order(user, restaurant);
//        BigDecimal totalAmount = BigDecimal.valueOf(0);
//        for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
//            Dish dish = dishRepository.findById(orderItemDto.getDishId())
//                    .orElseThrow(() -> new NotFoundException("Dish Not Found: " + orderItemDto.getDishId()));;
//            if (!dish.getRestaurant().getId().equals(restaurant.getId())) {
//                throw new ValidationException("Dish does not belong to this restaurant");
//            }
//            int count = orderItemDto.getCount();
//            if (count < 0) throw new ValidationException("Count cannot be negative: " + count);
//            OrderItem orderItem = new OrderItem(count, order, dish);
//            totalAmount = totalAmount.add(dish.getPrice().multiply(BigDecimal.valueOf(count)));
//
//            order.addOrderItem(orderItem);
//        }
//        if (totalAmount.compareTo(restaurant.getMinOrderAmount()) < 0) {
//            throw new ValidationException("Order amount is less than the minimum order amount in the restaurant");
//        }
//
//        orderRepository.save(order);
//
//        return modelMapper.map(order, OrderDto.class);
//    }
}

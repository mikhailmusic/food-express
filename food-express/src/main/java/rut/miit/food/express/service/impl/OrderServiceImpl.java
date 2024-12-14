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
import rut.miit.food.express.exception.*;
import rut.miit.food.express.repository.DishRepository;
import rut.miit.food.express.repository.OrderItemRepository;
import rut.miit.food.express.repository.OrderRepository;
import rut.miit.food.express.repository.UserRepository;
import rut.miit.food.express.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, DishRepository dishRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void addOrderItemToOrder(OrderItemAddDto itemDto) {
        User user = userRepository.findByUsername(itemDto.username()).orElseThrow(() -> new UserNotFoundException(itemDto.username()));
        Dish dish = dishRepository.findById(itemDto.dishId()).orElseThrow(() -> new DishNotFoundException(itemDto.dishId()));
        Restaurant restaurant = dish.getRestaurant();

        Order order = user.getOrders().stream()
                .filter(o -> o.getStatus().equals(OrderStatus.DRAFT) && o.getRestaurant().equals(restaurant))
                .findFirst().orElseGet(() -> new Order(user, restaurant));
        orderRepository.save(order);
        OrderItem item = order.addOrder(dish, itemDto.count());
        orderItemRepository.save(item);
    }

    @Override
    public void changeOrderItemToOrder(OrderItemUpdateDto dto) {
        OrderItem item = orderItemRepository.findById(dto.id()).orElseThrow(() -> new EntityNotFoundException("OrderItem Not Found: " + dto.id()));
        Order order = item.getOrder();
        checkUserAccess(order, dto.username());
        order.addOrder(item.getDish(), dto.newCount());
        orderItemRepository.update(item);
    }

    @Override
    public void createOrder(Integer id, String username) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        checkUserAccess(order, username);
        order.createOrder();
        orderRepository.update(order);
    }

    @Override
    public void cancelOrder(Integer id, String username) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        checkUserAccess(order, username);
        order.cancelOrder();
        orderRepository.update(order);
    }

    @Override
    public void changeStatus(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.nextStatus();
        orderRepository.update(order);
    }

    @Override
    public OrderDto getOrderDetails(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return toDto(order);
    }

    @Override
    public List<OrderDto> userOrdersDraft(String username) {
        List<Order> orders = orderRepository.findByUserAndStatus(username, OrderStatus.DRAFT);
        return orders.stream().filter(Order::notEmpty).map(this::toDto).toList();
    }

    @Override
    public List<OrderDto> restaurantOrders(Integer restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantIdStatus(restaurantId, Set.of(OrderStatus.CREATED,
                OrderStatus.CONFIRMED, OrderStatus.COOKING_PROCESS, OrderStatus.READY_FOR_DELIVERY, OrderStatus.OUT_FOR_DELIVERY)
        );
        return orders.stream().sorted(Comparator.comparing(Order::getCreationTime)).map(this::toDto).toList();
    }

    @Override
    public List<OrderDto> userOrdersHistory(String username) {
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime startTime = nowTime.minusDays(90);
        List<Order> orders = orderRepository.findByUser(username, startTime, nowTime);
        return orders.stream().sorted(Comparator.comparing(Order::getCreationTime).reversed()).map(this::toDto).toList();
    }

    private void checkUserAccess(Order order, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderAccessException(username);
        }
    }

    private OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        List<OrderItemDto> orderItems = order.getOrderItems().stream()
                .filter(item -> item.getCount() != 0)
                .map(item -> new OrderItemDto(item.getId(), item.getDish().getId(), item.getCount(),
                        item.getDish().getName(), item.getDish().getImageURL(), item.getDish().getVisible()))
                .sorted(Comparator.comparing(OrderItemDto::dishName)).toList();
        Review review = order.getReview();
        ReviewDto reviewDto = null;
        if (review != null){
            reviewDto = new ReviewDto(review.getText(), review.getRating(), review.getDate(), review.getUser().getFirstName());
        }
        BigDecimal total = order.getTotalAmount() == null ? order.calculateTotalAmount() : order.getTotalAmount();
        return new OrderDto(order.getId(), order.getCreationTime(), order.getDeliveryTime(), order.getStatus(), total,
                order.getRestaurant().getId(), order.getRestaurant().getName(), orderItems, reviewDto);
    }
}

package rut.miit.food.express.dto.order;

import rut.miit.food.express.dto.review.ReviewDto;
import rut.miit.food.express.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Integer id,
        LocalDateTime creationTime,
        LocalDateTime deliveryTime,
        OrderStatus status,
        BigDecimal totalAmount,
        Integer restaurantId,
        String restaurantName,
        List<OrderItemDto> orderItems,
        ReviewDto reviewDto
) {}

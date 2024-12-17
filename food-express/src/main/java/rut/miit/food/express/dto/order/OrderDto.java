package rut.miit.food.express.dto.order;

import rut.miit.food.express.dto.review.ReviewDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Integer id,
        LocalDateTime creationTime,
        LocalDateTime deliveryTime,
        String status,
        BigDecimal totalAmount,
        Integer restaurantId,
        String restaurantName,
        List<OrderItemDto> orderItems,
        ReviewDto reviewDto
) {}

package food.express.contracts.viewmodel.order;

import food.express.contracts.viewmodel.review.ReviewViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderInfoViewModel(
        Integer id,
        LocalDateTime creationTime,
        LocalDateTime deliveryTime,
        String status,
        BigDecimal totalPrice,
        Integer restaurantId,
        String restaurantName,
        List<OrderItemViewModel> orderItems,
        ReviewViewModel review
) {}

package food.express.contracts.viewmodel.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreateViewModel(
        Integer id,
        Integer restaurantId,
        String restaurantName,
        BigDecimal totalPrice,
        List<OrderItemViewModel> orderItems
) {}

package food.express.contracts.viewmodel.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRestaurantViewModel(
        Integer id,
        LocalDateTime creationTime,
        String status,
        List<OrderItemViewModel> orderItems
) {}

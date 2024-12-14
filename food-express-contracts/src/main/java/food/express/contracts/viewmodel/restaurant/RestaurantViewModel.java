package food.express.contracts.viewmodel.restaurant;

import java.math.BigDecimal;
import java.time.LocalTime;

public record RestaurantViewModel(
        Integer id,
        String name,
        String address,
        String description,
        String phoneNumber,
        LocalTime openTime,
        LocalTime closeTime,
        BigDecimal minOrderAmount
) {
}

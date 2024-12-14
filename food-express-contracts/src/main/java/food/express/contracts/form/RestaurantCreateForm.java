package food.express.contracts.form;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalTime;

public record RestaurantCreateForm(
        @NotBlank(message = "Название обязательно")
        @Size(min = 2, message = "Название должно содержать не менее 2 символов")
        String name,

        @NotBlank(message = "Адрес обязателен")
        @Size(min = 10, message = "Адрес должен содержать не менее 10 символов")
        String address,

        @Size(max = 700, message = "Описание не должно превышать 700 символов")
        String description,

        @NotBlank(message = "Номер телефона обязателен")
        @Pattern(regexp = "\\+?[0-9-]+", message = "Номер телефона некорректен (+, [0-9])")
        String phoneNumber,

        @NotNull(message = "Время открытия обязательно")
        LocalTime openTime,

        @NotNull(message = "Время закрытия обязательно")
        LocalTime closeTime,

        @NotNull(message = "Минимальная сумма заказа обязательна")
        @PositiveOrZero(message = "Минимальная сумма доставки должна быть положительным числом или 0")
        BigDecimal minOrderAmount
) {}

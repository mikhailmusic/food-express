package food.express.contracts.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemCreateForm(
        @NotNull(message = "Идентификатор пользователя обязателен")
        Integer userId,

        @NotNull(message = "Идентификатор блюда обязателен")
        Integer dishId,

        @NotNull(message = "Количество блюда в заказе обязательно")
        @PositiveOrZero(message = "Количество должно быть положительным числом")
        Integer count
) {}

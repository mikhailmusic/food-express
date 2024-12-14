package food.express.contracts.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemEditForm(
        @NotNull(message = "Идентификатор обязателен")
        Integer itemId,

        @NotNull(message = "Количество блюда в заказе обязательно")
        @PositiveOrZero(message = "Количество должно быть положительным числом")
        Integer count
) {}

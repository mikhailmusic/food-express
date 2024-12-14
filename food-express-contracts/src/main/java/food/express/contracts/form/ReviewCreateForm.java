package food.express.contracts.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewCreateForm(
        @NotNull(message = "Оценка обязательна")
        @Min(value = 1, message = "Оценка должна быть не менее 1")
        @Max(value = 5, message = "Оценка должна быть не более 5")
        Integer rating,

        @Size(max = 500, message = "Текст отзыва не должен превышать 500 символов")
        String text,

        @NotNull(message = "Идентификатор заказа обязателен")
        Integer orderId
) {}

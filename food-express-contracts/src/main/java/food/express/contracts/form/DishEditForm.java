package food.express.contracts.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DishEditForm(
        @NotNull(message = " обязателен")
        Integer id,

        @NotBlank(message = "Название блюда обязательно")
        @Size(min = 2, message = "Название блюда должно содержать не менее 2 символов")
        String name,

        @Size(max = 500, message = "Описание блюда не должно превышать 500 символов")
        String description,

        @NotNull(message = "Цена обязательна")
        @Positive(message = "Цена должна быть положительным числом")
        BigDecimal price,

        @NotNull(message = "Вес порции обязателен")
        @Positive(message = "Вес порции должен быть положительным числом")
        Integer weight,

        @NotNull(message = "Энергетическая ценность обязательна")
        @Positive(message = "Энергетическая ценность должна быть положительным числом")
        Integer calories,

        @NotBlank(message = "URL изображения обязателен")
        String imageURL,

        @NotNull(message = "Видимость блюда обязательна")
        Boolean isVisible,

        @NotNull(message = "Идентификатор категории обязателен")
        Integer categoryId
) {}

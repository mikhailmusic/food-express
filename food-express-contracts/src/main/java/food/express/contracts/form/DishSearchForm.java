package food.express.contracts.form;

import jakarta.validation.constraints.*;

public record DishSearchForm(
        String searchTerm,
        Integer categoryId,
        Boolean enableDish,

        @Min(value = 0, message = "Страница должна быть больше 0")
        Integer page,

        @Min(value = 1, message = "Размер страницы должен быть больше 0")
        Integer size

) {}


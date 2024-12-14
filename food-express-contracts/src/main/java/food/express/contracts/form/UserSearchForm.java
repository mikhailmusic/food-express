package food.express.contracts.form;

import jakarta.validation.constraints.Min;

public record UserSearchForm(
        String searchTerm,
        @Min(value = 0, message = "Страница должна быть больше 0")
        Integer page,
        @Min(value = 1, message = "Размер страницы должен быть больше 0")
        Integer size
) { }

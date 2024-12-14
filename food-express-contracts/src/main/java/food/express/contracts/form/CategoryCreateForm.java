package food.express.contracts.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateForm(
        @NotBlank(message = "Название категории обязательно")
        @Size(min = 2, message = "Название категории должно содержать не менее 2 символов")
        String name
) {}

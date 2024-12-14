package food.express.contracts.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserAdminEditForm(
        @NotNull(message = "Имя пользователя обязателено")
        String username,

        @NotBlank(message = "Роль пользователя обязательна")
        String role
) {}

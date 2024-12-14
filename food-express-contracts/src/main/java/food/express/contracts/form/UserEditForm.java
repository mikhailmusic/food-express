package food.express.contracts.form;

import jakarta.validation.constraints.*;


public record UserEditForm(
        @NotNull(message = "Имя пользователя обязателено")
        String username,

        @NotBlank(message = "Имя обязательно")
        @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
        String firstName,

        @Pattern(regexp = "^$|\\+?[0-9]+", message = "Номер телефона должен содержать только цифры и плюс")
        String phoneNumber,

        @NotBlank(message = "Адрес обязателен")
        @Size(min = 10, message = "Адрес должен содержать не менее 10 символов")
        String address
) {}

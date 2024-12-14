package food.express.contracts.form;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRegisterForm(
        @NotBlank(message = "Имя обязательно")
        @Size(min = 2, message = "Имя должно содержать не менее 2 символов")
        String firstName,

        @NotBlank(message = "Адрес обязателен")
        @Size(min = 10, message = "Адрес должен содержать не менее 10 символов")
        String address,

        @NotNull(message = "Дата рождения обязательна")
        @Past(message = "Дата рождения должна быть в прошлом")
        LocalDate birthDate,

        @NotBlank(message = "Логин обязателен")
        @Size(min = 5, message = "Логин должен содержать не менее 5 символов")
        String login,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
        String password,

        @NotBlank(message = "Подтверждение пароля обязательно")
        @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
        String confirmPassword
) {
        @AssertTrue(message = "Новый пароль и подтверждение должны совпадать")
        public boolean isPasswordsMatch() {
                return password != null && password.equals(confirmPassword);
        }
}

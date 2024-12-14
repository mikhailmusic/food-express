package food.express.contracts.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserPasswordChangeForm(
        @NotNull(message = "Имя пользователя обязателено")
        String username,

        @NotBlank(message = "Старый пароль обязателен")
        @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
        String oldPassword,

        @NotBlank(message = "Новый пароль обязателен")
        @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
        String newPassword,

        @NotBlank(message = "Подтверждение нового пароля обязательно")
        @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
        String confirmPassword
) {
    @AssertTrue(message = "Новый пароль и подтверждение должны совпадать")
    public boolean isPasswordsMatch() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }

}

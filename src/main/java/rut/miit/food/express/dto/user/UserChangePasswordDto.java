package rut.miit.food.express.dto.user;

public record UserChangePasswordDto(
        String username,
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {}

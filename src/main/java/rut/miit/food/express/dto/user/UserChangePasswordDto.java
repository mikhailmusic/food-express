package rut.miit.food.express.dto.user;

public record UserChangePasswordDto(
        Integer id,
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {}

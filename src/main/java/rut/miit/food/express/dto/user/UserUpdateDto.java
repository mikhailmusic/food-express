package rut.miit.food.express.dto.user;


public record UserUpdateDto(
        Integer id,
        String login,
        String firstName,
        String phoneNumber,
        String address
) {}

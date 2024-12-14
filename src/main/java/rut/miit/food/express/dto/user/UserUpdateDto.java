package rut.miit.food.express.dto.user;


public record UserUpdateDto(
        String username,
        String firstName,
        String phoneNumber,
        String address
) {}

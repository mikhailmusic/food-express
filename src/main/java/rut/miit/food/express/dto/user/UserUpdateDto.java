package rut.miit.food.express.dto.user;


public record UserUpdateDto(
        Integer id,
        String firstName,
        String phoneNumber,
        String address
) {}

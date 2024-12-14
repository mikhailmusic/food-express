package rut.miit.food.express.dto.user;

import java.time.LocalDate;

public record UserDto(
        Integer id,
        String firstName,
        String phoneNumber,
        String address,
        LocalDate birthDate,
        String login,
        String role
) {}

package rut.miit.food.express.dto.user;

import java.time.LocalDate;

public record UserAddDto(
        String firstName,
        String address,
        LocalDate birthDate,
        String login,
        String password,
        String confirmPassword
) {}

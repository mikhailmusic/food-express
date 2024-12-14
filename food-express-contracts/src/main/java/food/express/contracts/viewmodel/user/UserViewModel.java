package food.express.contracts.viewmodel.user;

import java.time.LocalDate;

public record UserViewModel(
        Integer id,
        String firstName,
        String phoneNumber,
        String address,
        LocalDate birthDate,
        String login,
        String role
) {}

package food.express.contracts.viewmodel.review;

import java.time.LocalDateTime;

public record ReviewViewModel(
        Integer rating,
        String text,
        LocalDateTime date,
        String userFirstName
) {}

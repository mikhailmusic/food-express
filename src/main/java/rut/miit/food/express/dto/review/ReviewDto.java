package rut.miit.food.express.dto.review;

import java.time.LocalDateTime;

public record ReviewDto(
        String text,
        Integer rating,
        LocalDateTime date
) {}

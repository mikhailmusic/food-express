package rut.miit.food.express.dto.dish;

import java.math.BigDecimal;

public record DishUpdateDto(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Integer weight,
        Integer calories,
        String imageURL,
        Boolean isVisible,
        Integer categoryId
) {}

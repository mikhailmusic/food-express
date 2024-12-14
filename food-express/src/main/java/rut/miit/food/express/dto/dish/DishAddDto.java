package rut.miit.food.express.dto.dish;

import java.math.BigDecimal;

public record DishAddDto(
        String name,
        String description,
        BigDecimal price,
        Integer weight,
        Integer calories,
        String imageURL,
        Integer restaurantId,
        Integer categoryId
) {}

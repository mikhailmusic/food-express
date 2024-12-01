package rut.miit.food.express.dto.dish;

import java.math.BigDecimal;

public record DishDto(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Integer weight,
        Integer calories,
        String imageURL,
        Integer categoryId,
        Integer restaurantId,
        String categoryName,
        String restaurantName
) {}

package rut.miit.food.express.dto.dish;

import java.util.List;

public record DishByCategoryDto(
        Integer id,
        String name,
        Integer count,
        List<DishDto> dishes
) {}

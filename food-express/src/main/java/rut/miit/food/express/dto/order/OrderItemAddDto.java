package rut.miit.food.express.dto.order;

public record OrderItemAddDto(
        Integer dishId,
        String username,
        Integer count
) {}

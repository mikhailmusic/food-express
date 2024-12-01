package rut.miit.food.express.dto.order;

public record OrderItemAddDto(
        Integer dishId,
        Integer userId,
        Integer count
) {}

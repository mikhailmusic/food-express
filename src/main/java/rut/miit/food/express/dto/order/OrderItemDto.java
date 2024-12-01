package rut.miit.food.express.dto.order;

public record OrderItemDto(
        Integer id,
        Integer dishId,
        Integer count,
        String dishName,
        String imageURL
) {}

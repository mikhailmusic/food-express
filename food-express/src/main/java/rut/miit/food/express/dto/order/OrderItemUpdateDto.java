package rut.miit.food.express.dto.order;

public record OrderItemUpdateDto(
        Integer id,
        Integer newCount,
        String username
) {}

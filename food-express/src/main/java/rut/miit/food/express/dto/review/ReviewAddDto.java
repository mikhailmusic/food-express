package rut.miit.food.express.dto.review;


public record ReviewAddDto(
        String username,
        Integer orderId,
        Integer rating,
        String text
) {}

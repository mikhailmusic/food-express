package rut.miit.food.express.dto.review;


public record ReviewAddDto(
        Integer orderId,
        Integer rating,
        String text
) {}

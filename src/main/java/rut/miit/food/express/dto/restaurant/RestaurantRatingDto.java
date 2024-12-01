package rut.miit.food.express.dto.restaurant;

public record RestaurantRatingDto(
        Integer id,
        String name,
        Integer countOrder,
        Integer countReview,
        Float averageRating
) {}
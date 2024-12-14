package food.express.contracts.viewmodel.restaurant;

public record RestaurantRatingViewModel(
        Integer id,
        String name,
        Integer countOrder,
        Integer countReview,
        Float rating
) {}

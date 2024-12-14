package food.express.contracts.viewmodel.restaurant;

import food.express.contracts.viewmodel.BaseViewModel;
import food.express.contracts.viewmodel.review.ReviewViewModel;

import java.util.List;

public record RestaurantReviewViewModel(
        BaseViewModel base,
        RestaurantViewModel restaurant,
        List<ReviewViewModel> reviews
) {}

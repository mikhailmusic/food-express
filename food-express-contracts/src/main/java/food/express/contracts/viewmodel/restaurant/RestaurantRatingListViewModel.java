package food.express.contracts.viewmodel.restaurant;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record RestaurantRatingListViewModel(
        BaseViewModel base,
        List<RestaurantRatingViewModel> restaurants
) {}

package food.express.contracts.viewmodel.restaurant;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record RestaurantListViewModel(
        BaseViewModel base,
        List<RestaurantViewModel> restaurants,
        Integer totalPages
) {}

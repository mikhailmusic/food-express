package food.express.contracts.viewmodel.restaurant;

import food.express.contracts.viewmodel.BaseViewModel;
import food.express.contracts.viewmodel.dish.DishByCategoryViewModel;

import java.util.List;

public record RestaurantInfoViewModel(
        BaseViewModel base,
        RestaurantViewModel restaurant,
        List<DishByCategoryViewModel> category
) {}

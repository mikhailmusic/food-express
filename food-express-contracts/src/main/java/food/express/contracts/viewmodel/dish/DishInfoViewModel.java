package food.express.contracts.viewmodel.dish;

import food.express.contracts.viewmodel.BaseViewModel;

public record DishInfoViewModel(
        BaseViewModel base,
        DishViewModel dishViewModel,
        String description,
        String categoryName,
        String restaurantName,
        Integer restaurantId
) {}

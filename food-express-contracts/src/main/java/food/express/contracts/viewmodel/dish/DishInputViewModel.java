package food.express.contracts.viewmodel.dish;

import food.express.contracts.viewmodel.BaseViewModel;
import food.express.contracts.viewmodel.category.CategoryViewModel;

import java.util.List;

public record DishInputViewModel(
        BaseViewModel base,
        List<CategoryViewModel> categories
) {}

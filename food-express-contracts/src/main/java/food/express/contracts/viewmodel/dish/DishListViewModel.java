package food.express.contracts.viewmodel.dish;

import food.express.contracts.viewmodel.BaseViewModel;
import food.express.contracts.viewmodel.category.CategoryViewModel;

import java.util.List;

public record DishListViewModel(
        BaseViewModel base,
        List<DishViewModel> dishes,
        List<CategoryViewModel> categories,
        Integer totalPages
) {}

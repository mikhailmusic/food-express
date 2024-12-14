package food.express.contracts.viewmodel.category;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record CategoryListViewModel(
        BaseViewModel base,
        List<CategoryViewModel> categories
) {}

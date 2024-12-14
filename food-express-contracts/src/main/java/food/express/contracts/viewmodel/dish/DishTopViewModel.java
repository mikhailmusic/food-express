package food.express.contracts.viewmodel.dish;

import food.express.contracts.viewmodel.BaseViewModel;
import java.util.List;

public record DishTopViewModel(
        BaseViewModel base,
        List<DishByCategoryViewModel> category
) {}

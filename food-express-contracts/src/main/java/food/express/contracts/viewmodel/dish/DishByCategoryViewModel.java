package food.express.contracts.viewmodel.dish;

import java.util.List;

public record DishByCategoryViewModel(
        Integer id,
        String name,
        List<DishViewModel> dishes,
        Integer count
) {}

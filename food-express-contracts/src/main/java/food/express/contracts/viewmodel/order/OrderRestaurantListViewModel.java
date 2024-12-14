package food.express.contracts.viewmodel.order;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record OrderRestaurantListViewModel(
        BaseViewModel base,
        List<OrderRestaurantViewModel> orders
) {}

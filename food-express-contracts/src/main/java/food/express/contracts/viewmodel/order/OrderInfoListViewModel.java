package food.express.contracts.viewmodel.order;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record OrderInfoListViewModel(
        BaseViewModel base,
        List<OrderInfoViewModel > orders
) {}

package food.express.contracts.viewmodel.order;

import food.express.contracts.viewmodel.BaseViewModel;


public record OrderViewModel(
        BaseViewModel base,
        OrderInfoViewModel order
) {}

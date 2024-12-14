package food.express.contracts.viewmodel.user;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record UserListViewModel(
        BaseViewModel base,
        List<UserViewModel> users,
        Integer totalPages
) {}

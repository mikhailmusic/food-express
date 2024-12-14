package food.express.contracts.viewmodel.user;

import food.express.contracts.viewmodel.BaseViewModel;

import java.util.List;

public record UserAdminEditViewModel(
        BaseViewModel base,
        String username,
        List<String> roles
) {}
